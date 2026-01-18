package com.mkt.br.gus.util.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class soundBeep {


    // Método para emitir som
    public static void beep() {
        try {
            Thread.sleep(100);  // Delay de 100ms antes de emitir o som

            // Acessando o arquivo de som dentro da pasta resources (somente com caminho relativo)
            InputStream soundStream = soundBeep.class.getResourceAsStream("/sounds/beep.wav");

            if (soundStream == null) {
                System.err.println("Arquivo de som não encontrado!");
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            System.err.println("Erro ao reproduzir o som: " + e.getMessage());
        }
    }
}