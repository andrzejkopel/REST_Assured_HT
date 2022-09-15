package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    long id;
    String name;
    List<String> photoUrls;
    List<String> tags;
    Status status;

    public enum Status {
        AVAILABLE,
        PENDING,
        SOLD
    }
}
