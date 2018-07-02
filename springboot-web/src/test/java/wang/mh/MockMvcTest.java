package wang.mh;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.mh.controller.HelloController;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * 使用MockMvc测试
 */
public class MockMvcTest {

    @Test
    public void testHello() throws Exception {
        String name = "hello,world";
        HelloController controller = new HelloController();
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();
        mvc.perform(MockMvcRequestBuilders.get("/hello").param("name", name))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("hello,world"))
                .andDo(print());
    }
}
