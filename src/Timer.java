public class Timer {
	long startTime;
	long endTime;
	
	double start() {
		startTime = System.currentTimeMillis();
		
		
		return startTime;
	}
	
	double stop(){
		endTime = System.currentTimeMillis();
		return endTime;
	}
	
	double getTime() {
		return endTime - startTime;
	}
}
