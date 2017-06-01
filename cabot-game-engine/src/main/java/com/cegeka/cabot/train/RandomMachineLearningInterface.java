package com.cegeka.cabot.train;

import com.cegeka.cabot.api.MachineLearningInterface;
import com.cegeka.cabot.api.beurt.Beurt;
import com.cegeka.cabot.api.beurt.Kaart;

import java.util.Random;

public class RandomMachineLearningInterface implements MachineLearningInterface{

    @Override
    public Kaart bepaalKaartOmTeSpelen(Beurt beurt, Kaart gespeeldDoorTegenstander) {
        int randomCardToPlay = new Random().nextInt(beurt.getBotHandkaarten().size());
        return beurt.getBotHandkaarten().get(randomCardToPlay);
    }

    @Override
    public void kenRewardToeVoorGespeeldeKaart(Beurt beurt, Kaart gespeeldDoorMLAlgo, int reward) {

    }
}