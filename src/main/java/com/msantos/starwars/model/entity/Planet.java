package com.msantos.starwars.model.entity;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "planets")
public class Planet {
	@Id
    private String id;
	
	@NotEmpty
	@Size(min = 1, max = 20)
	@Indexed(unique = true)
	private String name;
	
	@NotEmpty
	@Size(min = 1, max = 20)
	private String climate;
	
	@NotEmpty
	@Size(min = 1, max = 20)
	private String terrain;
	
	private Integer numberOfApparitions;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
	
	public Integer getNumberOfApparitions() {
		return numberOfApparitions;
	}

	public void setNumberOfApparitions(Integer number) {
		this.numberOfApparitions = number;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
}
