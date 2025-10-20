package Theresa.helper;

import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TheresaHelper {
    public static boolean canBecomeDust(AbstractCard c){
        if(DustPatch.dustManager.isFull())
            return false;
        if(c.hasTag(OtherEnum.Theresa_Darkness))
            return false;
        return c.type == AbstractCard.CardType.ATTACK || c.type == AbstractCard.CardType.SKILL;
    }
}
