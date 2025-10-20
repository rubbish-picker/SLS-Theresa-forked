package Theresa.helper;

import Theresa.card.attack.*;
import Theresa.card.power.*;
import Theresa.card.skill.*;
import Theresa.card.status.TheGift;
import Theresa.relic.BabelRelic;
import Theresa.relic.TheEnd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.logging.Logger;

public class RegisterHelper {
    public static ArrayList<String> startDeck = new ArrayList<>();
    public static ArrayList<String> startRelic = new ArrayList<>();

    public static ArrayList<AbstractCard> getCardsToAdd(){
        ArrayList<AbstractCard> list = new ArrayList<>();

        //BASIC
        list.add(new Strike());
        list.add(new Defend());
        list.add(new Spot());
        list.add(new Wish());
        //list.add(new DustStrike());
        //list.add(new DustDefend());
        list.add(new ADust());

        //COMMON
        list.add(new Oscillation());
        list.add(new Candle());
        list.add(new DeadOne());
        list.add(new TowardEndfield());
        list.add(new FromNight());
        list.add(new Iterate());
        list.add(new InFire());
        list.add(new WhereIsHome());
        list.add(new LittleTailor());
        list.add(new Babel());
        list.add(new WeaveSpread());
        list.add(new EmotionSame());

        //UNCOMMON
        list.add(new Silk());
        list.add(new Echoism());
        list.add(new PastDust());
        list.add(new YoreLinger());
        list.add(new DeathOfSarkaz());
        list.add(new LightlessShadow());
        list.add(new Forgive());
        list.add(new ThereItIs());
        list.add(new ImperialCourts());
        list.add(new WeaveTomorrow());
        list.add(new HoldDust());
        list.add(new TheAmiya());
        list.add(new TheDoctor());
        list.add(new TheCat());
        list.add(new TheSwordsman());
        list.add(new TheStrategist());
        list.add(new TheKiller());
        list.add(new TheDale());
        list.add(new InsideCrystal());
        list.add(new NarrativeSong());
        list.add(new ConnectedPain());
        list.add(new Terminate());
        list.add(new IdealReflection());
        list.add(new PastCallFuture());
        list.add(new IntoHistory());
        list.add(new MindOscillation());
        list.add(new DeadBreath());
        list.add(new Branch());
        list.add(new KingInheritance());
        list.add(new WhenItIsBye());

        //RARE
        list.add(new GuiltyWillEnd());
        list.add(new DustWithLight());
        list.add(new DustFalls());
        list.add(new Finale());
        list.add(new EternalDust());
        list.add(new TheTransformer());
        list.add(new TheBlooder());
        list.add(new TheBanshee());
        list.add(new TheWar());
        list.add(new ThousandsWish());
        list.add(new OnlyForYou());
        list.add(new TheAnswer());

        //OTHER
        list.add(new TheGift());
        list.add(new Originium());

        for(AbstractCard c : list){
            if(c.color == AbstractCard.CardColor.GREEN){
                Logger.getGlobal().info(c.getClass().getSimpleName());
            }
        }


        return list;
    }

    public static ArrayList<AbstractRelic> getRelicsToAdd(boolean onlyTheresa){
        ArrayList<AbstractRelic> list = new ArrayList<>();
        if(onlyTheresa){
            list.add(new BabelRelic());

            //BOSS
            list.add(new TheEnd());
        }
        else{

        }
        return list;
    }

    private static void initializeStart(){
        for(int i = 0; i < 4;i++){
            startDeck.add(Strike.ID);
        }
        for(int i = 0; i < 4;i++){
            startDeck.add(Defend.ID);
        }
        startDeck.add(Spot.ID);
        startDeck.add(Wish.ID);
        startDeck.add(ADust.ID);

        startRelic.add(BabelRelic.ID);
    }

    static {
        initializeStart();
    }
}
