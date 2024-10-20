package one.challenge.helper;

public class ExchangeResponse extends ApiResponse {
    public String result; 
	public String documentation; 
	public String terms_of_use; 
	public String time_last_update_utc; 
	public String time_next_update_utc; 
	public String base_code; 
	public String target_code; 
	public double conversion_rate; 
	public double conversion_result;
    @Override
    public String toString() {
        return base_code + " -> " + target_code + " : " + conversion_result;
    }
}
