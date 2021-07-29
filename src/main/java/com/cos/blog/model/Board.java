package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auro_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content; // 섬머노트 라이브러리를 사용하면 디자인이 될때 <html> 태그가 섞여서 디자인이 됨. -> 데이터의 크기가 매우 커짐
	
	// int는 '' 사용 x
	private int count; // 조회수
	
	// ORM에서는 조인이나 Foreign Key로 찾는게 아니라 바로 User 오브젝트를 사용하면 된다.
	// DB는 오브젝트를 사용할 수 없다. FK를 사용하는데 / 자바는 오브젝트를 저장할 수 있다.
	// DB는 오브젝트를 저장할 수 없기 때문에 실제로는 userId integer 필드가 생성된다.
	// 기본 전략 : fetch=FetchType.EAGER
	@ManyToOne(fetch=FetchType.EAGER) // Many = Board, One = User / 한명의 유저는 여러 개의 게시글을 쓸 수 있다.
	@JoinColumn(name="userId")
	private User user;
	
	// 기본 전략 : fetch=FetchType.LAZY
	// LAZY 는 댓글 펼치기 와 같은 UI 구성일 때, EAGER 은 상세 페이지에서 바로 댓글이 보일 때 -> 여기서는 보여줄 것이기 때문에 EAGER로 변경
	// cascade = CascadeType.REMOVE : 게시글 삭제시 해당 게시글의 Reply 전부 삭제
	@OneToMany(mappedBy="board", fetch=FetchType.EAGER, cascade = CascadeType.REMOVE) // @JoinColumn(name="replyId") 가 필요가 없다. // mappedBy : 연관관계의 주인이 아니다 (난 FK가 아니다), DB에 컬럼을 만들지 마라.
	@JsonIgnoreProperties({"board"}) // Reply에서 또 호출할 때 (Getter할 때) 이 변수 (컬럼)는 무시한다.
	@OrderBy("id desc") // board 를 select 할때 Reply의 id를 내림차순으로 해서 List에 들고 온다.
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
}
