package Theresa.modcore;

import Theresa.action.PlayCardAction;
import Theresa.character.Theresa;
import Theresa.helper.CivilightHelper;
import Theresa.helper.RegisterHelper;
import Theresa.patch.*;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Properties;

@SpireInitializer
public class TheresaMod implements PreStartGameSubscriber,PreUpdateSubscriber,RenderSubscriber,StartGameSubscriber,OnPlayerTurnStartPostDrawSubscriber,PostBattleSubscriber,OnPlayerTurnStartSubscriber,OnStartBattleSubscriber,PostInitializeSubscriber,EditStringsSubscriber, EditKeywordsSubscriber, EditCardsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber {
    private static final Logger logger = LogManager.getLogger(TheresaMod.class);

    public static Color tColor = new Color(234f/255f,221f/255f,212f/255f,1);

    public static final String cardBg512 = "TheresaResources/img/512/cardbg.png";
    public static final String energy512 = "TheresaResources/img/512/energybg.png";
    public static final String cardBg1024 = "TheresaResources/img/1024/cardbg.png";
    public static final String energy1024 = "TheresaResources/img/1024/energybg.png";
    
    public static void initialize(){
        new TheresaMod();

        try{
            Properties defaults = new Properties();
            defaults.setProperty("defaultType","0");
            SpireConfig config = new SpireConfig("Theresa_FimmlpS","Common",defaults);
            DefaultTypeIndex = config.getInt("defaultType");

        }catch (IOException var1){
            var1.printStackTrace();
        }
    }
    
    public static void logSomething(String message){
        logger.info(message);
    }

    public static int DefaultTypeIndex = 0;
    
    public TheresaMod(){
        BaseMod.subscribe(this);
        BaseMod.addColor(ColorEnum.Theresa_COLOR,tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),cardBg512,cardBg512,cardBg512,energy512,cardBg1024,cardBg1024,cardBg1024,energy1024,"TheresaResources/img/orbs/EnergyOrb.png");
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new Theresa("Theresa"),
                "TheresaResources/img/charSelect/TheresaButton.png",
                "TheresaResources/img/charSelect/TheresaBG.png",
                ClassEnum.Theresa_CLASS
        );
    }

    @Override
    public void receiveEditStrings() {
        String relic = "relics.json",
                card = "cards.json",
                power= "powers.json",
                potion="potions.json",
                event="events.json",
                character="characters.json",
                ui="uis.json",
                monster="monsters.json",
                score ="scores.json";
        String fore = "TheresaResources/localizations/";
        String lang;
        if(Settings.language == Settings.GameLanguage.ZHS||Settings.language == Settings.GameLanguage.ZHT){
            lang = "zh";
        }
        else{
            lang = "en";
        }
        relic = fore + lang + "/" + relic;
        card = fore + lang + "/" + card;
        power = fore + lang + "/" + power;
        potion = fore + lang + "/" + potion;
        event = fore + lang + "/" + event;
        character = fore + lang + "/" + character;
        ui = fore + lang + "/" + ui;
        monster = fore + lang + "/" + monster;
        score = fore + lang + "/" + score;

        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        //String eventStrings = Gdx.files.internal(event).readString(String.valueOf(StandardCharsets.UTF_8));
        //BaseMod.loadCustomStrings(EventStrings.class,eventStrings);
        String characterStrings = Gdx.files.internal(character).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class,characterStrings);
        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class,uiStrings);
        //String monsterStrings = Gdx.files.internal(monster).readString(String.valueOf(StandardCharsets.UTF_8));
        //BaseMod.loadCustomStrings(MonsterStrings.class,monsterStrings);
        //String scoreStrings = Gdx.files.internal(score).readString(String.valueOf(StandardCharsets.UTF_8));
        //BaseMod.loadCustomStrings(ScoreBonusStrings.class,scoreStrings);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "en";
        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            lang = "zh";
        }
        else {
            lang = "en";
        }

        String json = Gdx.files.internal("TheresaResources/localizations/"+lang+"/keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (Keyword[])gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            Keyword[] var5 = keywords;
            int var6 = keywords.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Keyword keyword = var5[var7];
                BaseMod.addKeyword("theresa", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditCards() {
        for (AbstractCard c : RegisterHelper.getCardsToAdd()) {
            BaseMod.addCard(c);
        }
    }

    @Override
    public void receiveEditRelics() {
        for(AbstractRelic r: RegisterHelper.getRelicsToAdd(true)){
            BaseMod.addRelicToCustomPool(r,ColorEnum.Theresa_COLOR);
        }

        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(false)){
            BaseMod.addRelic(r, RelicType.SHARED);
        }
    }

    @Override
    public void receiveStartGame() {
        DustPatch.preBattle();
    }

    @Override
    public void receivePreStartGame() {
        PlayCardAction.clear();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        //DustPatch.preBattle();
        CivilightHelper.atBattleStart();
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        DustPatch.postBattle();
        PlayCardAction.clear();
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        PlayCardAction.clear();
        DustPatch.atTurnStart();
        SilkPatch.atTurnStart();
    }

    @Override
    public void receiveOnPlayerTurnStartPostDraw() {
        DustPatch.atTurnStartPostDraw();
    }

    @Override
    public void receivePostInitialize() {
        unlockCards();
        unlockRelics();
    }

    @Override
    public void receiveRender(SpriteBatch spriteBatch) {
        //BlackPatch.render(spriteBatch);
    }

    @Override
    public void receivePreUpdate() {
        //BlackPatch.update();
    }

    private static void unlockCards(){
        Iterator<AbstractCard> var1 = RegisterHelper.getCardsToAdd().iterator();
        while (var1.hasNext()){
            AbstractCard c = var1.next();
            String key = c.cardID;
            AbstractCard tmp = CardLibrary.getCard(key);
            if (tmp != null && !CardLibrary.getCard(key).isSeen) {
                tmp.isSeen = true;
                tmp.unlock();
                UnlockTracker.seenPref.putInteger(key, 1);
            }
        }
        UnlockTracker.seenPref.flush();
    }

    private static void unlockRelics(){
        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(true)){
            UnlockTracker.markRelicAsSeen(r.relicId);
        }

        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(false)){
            UnlockTracker.markRelicAsSeen(r.relicId);
        }
    }
}
