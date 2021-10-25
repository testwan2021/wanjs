package java.ooa;

public class OOA {
    /*
        举例：人去洗衣服
        面向对象：将程序和数据进行封装，以提高软件的可用性 灵活性和拓展性
        洗衣机对象：加水  清洗 甩干
        人对象：开洗衣机 放入衣服
        面向对象的三大特征：封装 继承  多态
        封装：主要是把属性私有化，同时又可以提供下外部可以访问的方法，方便我们可以修改内的内部实现的方法
        继承：
        1.子类得到父类非private属性和方法
        2.子类可以对父类进行拓展
        缺点：父类变子类就要改，继承破坏了封装，继承属于强耦合
        多态：（实现形式接口和继承）

        重写->父类与子类的多态性，对父类的方法内容进行重写
        父子类方法：相同的名称和参数，子类的修饰符要比父类大，跑出的异常要比父类小
        重载：是让类以统一的方式处理不同的数据类型的手段->在一个类中， 多个方法名相同，不同的参数（不同参数个数和参数类型），和返回值类型无关
             可以抛出不同的异常

        面向过程：开洗衣机->放入衣服->加水->清洗->甩干
        按照顺序，按照功能划分模块，每个模块都是由顺序，选择和循环等流程组成
     */

    private String name;//私有
    //方法外部访问调用public修饰
    public String getName() {
        return name;
    }

    //重载
    void eat(){}
    void eat(int  a,String flag ){ }
    void eat(int  a ){}
}