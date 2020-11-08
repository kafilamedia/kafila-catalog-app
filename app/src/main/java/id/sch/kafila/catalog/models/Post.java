package id.sch.kafila.catalog.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

import id.sch.kafila.catalog.util.Navigate;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Post implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 5563148598862171231L;

	private int id;
	private String
    author,
    title,
    slug,
    content,
    excerpt,
    type,
    date,
    release;
	// author_id
	@JsonProperty("author_id")
	private String authorId;
	@JsonProperty("authorId")
	public void setAuthorIdJson(String id){
		authorId = id;
	}
	private PostImage images;

	public String newsLink(){
		String mainUrl = "https://kafila.sch.id/#!/read/";
		return mainUrl+getSlug();
	}
}
