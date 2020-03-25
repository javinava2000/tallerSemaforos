package tallerSemaforos;
import java.util.Random;

public class consumer extends Thread{
	
	public consumer() {
		start();
	}
	
	private void consume() {
		
		Random rdmNum = new Random();
		int sleepTime = rdmNum.nextInt(250 - 25 + 1) + 25;
		
		try {
			sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Consumir el elemento
		int consumedNumber = buffer.getStore().poll();
		System.out.println("Consumidor: Número " + consumedNumber + " consumido.");
		
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			if(buffer.getStore().size() == 0) {
				
				System.out.println("Consumidor: El buffer está vacio, esperando a que el productor trabaje.");
			}
			
			try {
				buffer.getsNoVacio().acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			consume();
			
			buffer.getsNoLleno().release();
		}
	}
}
