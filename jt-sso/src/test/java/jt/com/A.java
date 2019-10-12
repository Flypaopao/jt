package jt.com;


public class A extends Thread
{
   public void run()
  {
    System.out.print(0);
  }
   
  public static void main(String []args)
  {
    A a=new A();
    a.start(); 
   }

}
