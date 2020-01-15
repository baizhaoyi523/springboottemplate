package com.bzy.springtemplate;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@DBRider
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApplicationTest {

    @Test
    @DataSet({"test.yml"})
    void should_insert_data() {
    }

}
