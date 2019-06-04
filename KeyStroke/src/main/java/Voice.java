import com.sun.speech.freetts.VoiceManager;

public class Voice {

	private String name; // name of voice we want to use
	private com.sun.speech.freetts.Voice voice; // Create empty instance of voice class within sun

	public Voice getVoice(){return this;}

	public Voice(String name) {
		System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		this.name = name;
		this.voice = VoiceManager.getInstance().getVoice(this.name); // get the voice instance
		if(voice != null){
			this.voice.allocate();
			try {
				voice.setRate(150);// Setting the rate of the voice
				voice.setPitch(100);// Setting the Pitch of the voice
				voice.setVolume(3);// Setting the volume of the voice

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else{
			throw new IllegalStateException("Cannot find voice: "+name);
		}

	}

	public void mute(boolean flag){if(flag)voice.setVolume(0); else voice.setVolume(3);}

	public void say(String something){
		voice.getAudioPlayer().cancel();
		new Thread(()->this.voice.speak(something)).start();
	}

	public static void main(String[] args) {
		Voice voice = new Voice("kevin16");
		voice.say("\"On the other hand, we denounce with righteous indignation and " +
				"dislike men who are so beguiled and demoralized by the charms of pleasure of" +
				" the moment, so blinded by desire, that they cannot foresee the pain and " +
				"trouble that are bound to ensue; and equal blame belongs to those who fail " +
				"in their duty through weakness of will, which is the same as saying through " +
				"shrinking from toil and pain. These cases are perfectly simple and easy to " +
				"distinguish. In a free hour, when our power of choice is untrammelled and when" +
				" nothing prevents our being able to do what we like best, every pleasure is " +
				"to be welcomed and every pain avoided. But in certain circumstances and owing " +
				"to the claims of duty or the obligations of business it will frequently occur " +
				"that pleasures have to be repudiated and annoyances accepted. The wise man " +
				"therefore always holds in these matters to this principle of selection: he " +
				"rejects pleasures to secure other greater pleasures, or else he endures pains " +
				"to avoid worse pains.\"\n" +
				"\n");
	}
}
