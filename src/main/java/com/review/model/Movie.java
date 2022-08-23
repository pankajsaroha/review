package com.review.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false)
    private String name;
    @NonNull
    @ElementCollection
    private List<String> cast;
    private int releaseYear;
    private String genre;
}
