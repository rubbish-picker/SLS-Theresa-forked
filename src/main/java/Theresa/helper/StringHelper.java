package Theresa.helper;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class StringHelper {
    public static final String MOD_ID = "theresa:";
    public static final UIStrings TAGS = CardCrawlGame.languagePack.getUIString("theresa:Tags");
    public static final UIStrings OPERATION = CardCrawlGame.languagePack.getUIString("theresa:Operation");

    public static String getRelicIMGPATH(String ID,boolean outline){
        //replace
        ID = ID.replace(MOD_ID,"");
        if(outline){
            return "TheresaResources/img/relics/"+ID+"_O.png";
        }
        return "TheresaResources/img/relics/"+ID+".png";
    }

    public static String getCardIMGPath(String ID, AbstractCard.CardType type) {
        ID = ID.replace(MOD_ID, "");
        switch (type) {
            case ATTACK: {
                ID += "_attack";
                break;
            }
            case CURSE:
            case STATUS:
            case SKILL: {
                ID += "_skill";
                break;
            }
            case POWER: {
                ID += "_power";
                break;
            }
        }
        String path = "TheresaResources/img/cards/" + ID + ".png";
        if(Gdx.files.internal(path).exists()) {
            return path;
        }
        return getTempCardIMGPath(type);
    }

    public static String getTempCardIMGPath(AbstractCard.CardType type){
        switch (type) {
            case ATTACK: {
                return "TheresaResources/img/cards/Strike_attack.png";
            }
            case CURSE:
            case STATUS:
            case SKILL: {
                return "TheresaResources/img/cards/Defend_skill.png";
            }
            case POWER: {
                return "TheresaResources/img/cards/Paveon_power.png";
            }
        }
        return "TheresaResources/img/cards/Defend_skill.png";
    }
}
