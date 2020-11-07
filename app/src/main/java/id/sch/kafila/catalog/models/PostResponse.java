package id.sch.kafila.catalog.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostResponse implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -2328541386817117984L;
	private ResponseCategory category;
	private int total;
	@JsonAlias({"current_page", "currentPage"})
	private int currentPage;
	@JsonAlias({"perPage","per_page"})
	private int perPage;
	
	private Object posts;
	
	//@JsonIgnore
	private List<Post > agendas;
//	@JsonIgnore
	private NewsPost newsPost;
}
