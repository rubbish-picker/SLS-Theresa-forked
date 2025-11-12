package Theresa.helper;

import Theresa.modifier.ForgetMod;
import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.silk.MindSilk;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class TheresaHelper {
    public static boolean canBecomeDust(AbstractCard c){
        return canBecomeDust(c,false);
    }

    public static boolean canBecomeDust(AbstractCard c, boolean ignoreMax){
        if(!ignoreMax && DustPatch.dustManager.isFull())
            return false;
        if(c.hasTag(OtherEnum.Theresa_Darkness))
            return false;
        return c.type == AbstractCard.CardType.ATTACK || c.type == AbstractCard.CardType.SKILL;
    }

    public static int getForgetAmount(AbstractCard c){
        return CardModifierManager.getModifiers(c, ForgetMod.ID).size();
    }

    public static boolean drawnAsDust(AbstractCard c){
        return DustPatch.CardScaleField.drawnAsDust.get(c);
    }

    public static boolean connectedToHH(AbstractCard c){
        ArrayList<String> specificKeywords = new ArrayList<>();
        specificKeywords.add(StringHelper.TAGS.TEXT[5]);
        specificKeywords.add(StringHelper.TAGS.TEXT[6]);
        specificKeywords.add(StringHelper.TAGS.TEXT[7]);
        for(String keyword: c.keywords){
            if(specificKeywords.contains(keyword))
                return true;
        }
        return false;
    }
}
