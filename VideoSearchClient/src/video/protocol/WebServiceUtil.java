package video.protocol;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class WebServiceUtil {
	// �����ռ�
	//final String SERVICE_NS = "http://yhtt2020.vicp.cc/VideoSearchService";
	final static String SERVICE_NS = "http://yhtt2020.vicp.cc/VideoSearchService";
	// �����ַ
	//final static String SERVICE_URL = "http://www.coolsou.com/SearchEngine.asmx";
	final static String SERVICE_URL = "http://www.coolsou.com/SearchEngine.asmx";
	// ACTION
	final String SERVICE_ACTION =SERVICE_NS;
	// static final String SERVICE_ACTION=SERVICE_NS;
	private SoapObject soapObject = null;

	public WebServiceUtil(String operation) {
		soapObject = new SoapObject(SERVICE_NS, operation);
	}

	public void AddPropertyParameter(String name, Object value) {
		soapObject.addProperty(name, value);
	}

	public void AddPropertyParameter(PropertyInfo pi) {
		soapObject.addProperty(pi);
	}

	public void AddAttributeParameter(String name, Object value) {
		soapObject.addAttribute(name, value);
	}

	public HttpTransportSE CreateTransportSE() {
		HttpTransportSE httpTransportSE = new HttpTransportSE(SERVICE_URL);
		httpTransportSE.debug = true;
		return httpTransportSE;
	}

	public SoapSerializationEnvelope CreateEnvelope() {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		return envelope;
	}

	public SoapObject GetResult() {
		String methodNameString = soapObject.getName();
		SoapObject resultObject=null;
		SoapSerializationEnvelope envelope = CreateEnvelope();
		try {
			HttpTransportSE se = CreateTransportSE();
			//se.call(null, envelope);
			se.call(SERVICE_ACTION+"/"+ methodNameString, envelope);
			
			if (envelope.getResponse() != null) {
				//�����Ϊ�գ�ֱ�ӷ���Soap����
				resultObject = (SoapObject) envelope.bodyIn;
				
				return resultObject;
			}
			//���Ϊ��
		} 
		catch (XmlPullParserException  e) {
			Log.e("����",e.getMessage());
			return null;
		}
		catch (IOException e) {
			Log.e("����",e.getMessage());
			return null;
		}
		//
		return resultObject;
	}

}
