package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;

class DigitalClockLabel extends Label {
	static String maintainedTime = "";
	static String currTime = "";
	public DigitalClockLabel() {
		bindToTime();
		this.setAlignment(Pos.CENTER);
	}

	private void bindToTime() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				// set your time here
				currTime= getRealTime();
				if(currTime==null || currTime.contains("99")) {
					setText(maintainedTime);
				}else {
					setText(currTime);
					maintainedTime = currTime;
				}
				setTextFill(Color.WHITE);
				setBackground(new Background(new BackgroundFill(Color.web("#00a2ed"),CornerRadii.EMPTY, Insets.EMPTY)));
			}
		}), new KeyFrame(Duration.seconds(40)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	private String getRealTime() {
		StringBuffer response = new StringBuffer();
		String finalAns = null;
		try {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.tcs.com", 8080));
			URL obj = new URL("http://worldtimeapi.org/api/timezone/Asia/Kolkata");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection(proxy);
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			//responseString.indexOf('T', responseString.indexOf("unix")));// is 272
			int[] no = new int[2];
			String[] ansarr = response.toString().substring(274, 279).split(":");
			if(ansarr[0]!=null) {
				no[0] = Integer.valueOf(ansarr[0]);
			}else {
				no[0] = 99;
			}
			if(ansarr[1]!=null) {
			no[1] = Integer.valueOf(ansarr[1]);
			}else {
				no[1] = 99;
			}
			if(no[0]>=12) {
				finalAns = no[0]-12+":"+no[1]+" PM";
			}else {
				finalAns = no[0]+":"+no[1]+" AM";
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return finalAns;//;
	}
}