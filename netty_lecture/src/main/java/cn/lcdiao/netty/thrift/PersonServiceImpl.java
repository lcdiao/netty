package cn.lcdiao.netty.thrift;


import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * Created by diao on 2019/3/1
 */
public class PersonServiceImpl implements PersonService.Iface {
    public Person getPersionByUsername(String username) throws DataException, TException {
        System.out.println("Got Client Param: "+username);

        Person person = new Person();
        person.setUsername(username);
        person.setAge(20);
        person.setMarried(false);
        return  person;
    }

    public void savePerson(Person person) throws DataException, TException {
        System.out.println("Got Client param: " );
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
