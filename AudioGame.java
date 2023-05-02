import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioGame {
    


    private Clip clip;

    
    public AudioGame(String nom){
        try{
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(nom));
            clip = AudioSystem.getClip();
            clip.open(audio);
        }
        catch(Exception e){}
    }
    public Clip getClip() {
        return clip;
    }


    public void play(){clip.start();}
    public void stop(){clip.stop();}
    public static void playSound(String nom){
        AudioGame s = new AudioGame(nom);
        s.play();
    }
}

