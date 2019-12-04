package threading

import org.scalatest.FunSuite

class WaitNotify1Spec extends FunSuite {

  test("wait + notify: this can be used to introduce data every X items"){
    val lock = new AnyRef
    var msg: String = ""

    val t1 = new Thread {
      override def run(): Unit = lock.synchronized{
        for(x <- 1 to 3){
          msg = msg + x
        }
        println("Thread Id: " + Thread.currentThread.getId +" waiting")
        lock.wait()
        println("Thread Id: " + Thread.currentThread.getId +" notified")

        for(x <- 4 to 6){
          msg = msg + x
          println(msg)
        }
      }
    }

    t1.start()

    Thread.sleep(2000)
    lock.synchronized {
      for(_ <- 1 to 3) {
        msg = msg + "c"
        println(msg)
      }
      lock.notify()
    }

    t1.join()
  }
}

