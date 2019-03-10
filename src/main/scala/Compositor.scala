import scala.annotation.tailrec

case class Compositor[T](functions: (T => T)*){

  def apply(input: T): T = {
    @tailrec
    def applyImpl(output: T, stack: (T => T)*): T = {
      if(stack.isEmpty) output
      else applyImpl(stack.head(output), stack.tail:_*)
    }
    applyImpl(input, functions:_*)
  }

  def ++(that: Compositor[T]): Compositor[T] = Compositor[T](functions ++ that.functions:_*)

}