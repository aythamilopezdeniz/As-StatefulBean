package ejbs;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.ejb.Remove;

@Stateful
@LocalBean
public class Cart {
    ArrayList list = new ArrayList();
    
    @PostConstruct
    void create() {
        list.add("PostConstruct, ");
    }
    
    public void add (String s){
        list.add(s);
    }
    
    public ArrayList getList() {
        return list;
    }
    
    @Remove
    public void remove() {
        System.out.println("::PreDestroy::");
        list.removeAll(list);
    }
}