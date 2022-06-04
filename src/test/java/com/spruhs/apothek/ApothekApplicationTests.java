package com.spruhs.apothek;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApothekApplicationTests {

    @Autowired
    ModelMapper modelMapper;

    @Test
    void contextLoads() {
        Assertions.assertThat(modelMapper).isNot(null);
    }

}
