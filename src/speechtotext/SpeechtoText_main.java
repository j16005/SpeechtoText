package speechtotext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

public class SpeechtoText_main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		 SpeechToText service = new SpeechToText();
		 service.setUsernameAndPassword("7bf2a504-26e0-4e66-93a8-d7b417432ac1", "aaVxbPjYkkbT");

		    File audio = new File("./audio/output.wav");
		    RecognizeOptions options =null;

		    MySQL mysql = new MySQL();
			try {
				options = new RecognizeOptions.Builder()
						.model("ja-JP_BroadbandModel")
				    .audio(audio)
				    .contentType(RecognizeOptions.ContentType.AUDIO_WAV)
				    .build();
			} catch (FileNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		    SpeechRecognitionResults transcript = service.recognize(options).execute();

		    //JsonNode node=null;
		    String s=String.valueOf(transcript);
		    ObjectMapper mapper = new ObjectMapper();
		    try {
				 //node = mapper.readTree(transcript.toString());
		    	JsonNode node=mapper.readTree(s);
		    	for(int i=0;i<node.get("results").size();i++){
		    		String text = node.get("results").get(0).get("alternatives").get(0).get("transcript").toString();
		    		System.out.println("transcript : "+text);
		    		double confidence =node.get("results").get(0).get("alternatives").get(0).get("confidence").asDouble();
		    		System.out.println("confidence : "+confidence);
		    		mysql.updateImage(text,confidence);
		    	}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}


		}

	}

