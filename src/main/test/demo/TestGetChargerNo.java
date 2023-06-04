package demo;

import org.junit.Test;

import java.util.Random;

/**
 * @author DingSH
 * @version 1.0
 * @Date 2023-06-03 22:10
 */
public class TestGetChargerNo {
    @Test
    public void test01(){
        StringBuffer sb = new StringBuffer("PSY");
        for (int i = 1; i <= 13; i++) {
            int num = (int) Math.ceil(new Random().nextInt(9));
            sb.append(num);
        }
        System.out.println(sb.toString());
    }
}
