package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly=true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		}); // 영속화 완료
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시 (Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹 -> 자동 업데이트가 됨. DB Flush
	}

	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		/*
		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 유저 ID를 찾을 수 없습니다.");
		}); // 영속화
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 ID를 찾을 수 없습니다.");
		}); // 영속화		
		// 방법 1
		Reply reply = Reply.builder().user(user).board(board).content(replySaveRequestDto.getContent()).build();
		// 방법 2 (Reply에 새로운 함수 만들어서 사용)
		// 굳이...
//		Reply reply = new Reply();
//		reply.update(user, board, replySaveRequestDto.getContent());
		replyRepository.save(reply);
		*/
		
		// ------------------- 위와는 별개로 ------------------
		// 영속화를 안하는 방법!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
}
