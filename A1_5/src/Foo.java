public class Foo {
   private Random random 

   public Foo() {
       this(System.currentTimeMillis());
   }

   public Foo(long seed) {
       this.random = new Random(seed);
   }

   public synchronized double getNext() {
        return generator.nextDouble();
   }
}
