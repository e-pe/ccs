/**
 * 
 */
package CCS.Application.Services;

/**
 * @version 1.0
 * @since June 3, 2009
 */
class GnuiteView implements IServiceView {
	private static final String SourceAddress = "saddr";
	private static final String DestinationAddress = "daddr";
	
	private Double srcLatitude;
	private Double srcLongitude;
	private Double dstLatitude;
	private Double dstLongitude;
	
	private GnuiteService service;
	
	public GnuiteView(GnuiteService service){
		this.service = service;
	}
	
	public void setSrcLatitude(Double srcLatitude){
		this.srcLatitude = srcLatitude;
	}
	
	public void setSrcLongitude(Double srcLongitude){
		this.srcLongitude = srcLongitude;
	}
	
	public void setDstLatitude(Double dstLatitude){
		this.dstLatitude = dstLatitude;
	}
	
	public void setDstLongitude(Double dstLongitude){
		this.dstLongitude = dstLongitude;
	}
	
	public String getUrl(){
		String url = this.service.getConnectionString() + "?";
		url += SourceAddress + "=" + this.srcLatitude + "," + this.srcLongitude + "&";
		url += DestinationAddress + "=" + this.dstLatitude + "," + this.dstLongitude; 
		
		return url;
	}
}
