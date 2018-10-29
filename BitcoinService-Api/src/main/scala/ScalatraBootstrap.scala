import com.bitcoin.helper.BitcoinServiceImpl
import com.bitcoin.service.BitcoinServlet
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new BitcoinServlet(new BitcoinServiceImpl), "/bitcoin/*")
  }
}
