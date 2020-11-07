package id.sch.kafila.catalog.models;

import java.io.Serializable;

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
	private PostImage images;
}
