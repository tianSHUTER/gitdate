package io.kimmking.dubbo.demo.consumer;


import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DubboClientApplication {

	@DubboReference(version = "1.0.0") //, url = "dubbo://127.0.0.1:12345")
	private UserService userService;


	public static void main(String[] args) {

		SpringApplication.run(DubboClientApplication.class).close();

		// UserService service = new xxx();
		// service.findById

//		UserService userService = Rpcfx.create(UserService.class, "http://localhost:8080/");
//		User user = userService.findById(1);
//		System.out.println("find user id=1 from server: " + user.getName());
//
//		OrderService orderService = Rpcfx.create(OrderService.class, "http://localhost:8080/");
//		Order order = orderService.findOrderById(1992129);
//		System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));

	}

	@Bean
	public SpringHmilyTransactionAspect springHmilyTransactionAspect(){
		return new SpringHmilyTransactionAspect();
	}

	@Bean
	public HmilyApplicationContextAware HmilyApplicationContextAware(){
		return new HmilyApplicationContextAware();
	}

	@Bean
	public ApplicationRunner runner() {
		return args -> {
			BIConversion.User user = userService.exchange2doller(7);
			System.out.println("转换之后的doller"+user.getMoney_doller());
		};
	}

}
