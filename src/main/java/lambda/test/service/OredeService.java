package lambda.test.service;

import org.springframework.stereotype.Service;

@Service
public class OredeService {


    public boolean addOrder() {
        System.out.println("db............正在操作");
        return true;
    }
}
