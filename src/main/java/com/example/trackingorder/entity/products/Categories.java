package com.example.trackingorder.entity.products;

import com.example.trackingorder.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categories extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36)
    private String id;


    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Products> products;

    // category cha
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Categories parent;

    // danh sách category con
    @OneToMany(mappedBy = "parent")
    private List<Categories> children ;
}
