package tallerSemaforos;
import java.util.Random;

public class producer extends Thread{
	
	public producer() {
		start();
	}
	
	private void produce() {
		Random rdmNum = new Random();
		int numP = rdmNum.nextInt(999) + 1;
		int sleepTime = rdmNum.nextInt(250 - 25 + 1) + 25; //Rango (max - min + 1) + min
		
		try {
			sleep(sleepTime);
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Productor: N�mero " + numP + " producido.");
		
		//A�adir al buffer
		buffer.getStore().add(numP);
	}
	
	@Override
	public void run () {
		
		while(true) {
			
			if(buffer.getStore().size() == buffer.bSize) {
				System.out.println("Productor: El buffer est� lleno, esperando a que el consumidor trabaje.");
			}
			
			try {
				buffer.getsNoLleno().acquire();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			produce();
			
			buffer.getsNoVacio().release();
		}
	}
}
