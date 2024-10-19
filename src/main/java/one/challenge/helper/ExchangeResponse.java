package one.challenge.helper;

public class ExchangeResponse {
    String result; 
	String documentation; 
	String terms_of_use; 
	String time_last_update_utc; 
	String time_next_update_utc; 
	String base_code; 
	String target_code; 
	double conversion_rate; 
	double conversion_result;
    @Override
    public String toString() {
        return base_code + " -> " + target_code + " : 1 -> " + conversion_result;
    }
}
