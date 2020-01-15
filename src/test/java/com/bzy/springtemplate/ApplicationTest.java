package com.bzy.springtemplate;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@DBRider
@SpringBootTest
class ApplicationTest {

    @Test
    @DataSet({"test.yml"})
    void should_insert_data() {
        System.out.println("111111111");
    }

}
