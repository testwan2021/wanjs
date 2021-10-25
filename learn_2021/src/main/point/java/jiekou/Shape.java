package java.jiekou;

public abstract   class Shape {
    private static int a=10;
    //含有抽象方法的类叫做抽象类
    public   double area(){
        return 0l;
    };//定义形状面积 ，如果方法没有具体实现（就是方法后面 没有{}），那么必须加上abstract来声明这个方法
}
