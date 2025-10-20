package Theresa.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CardPatch {

    //更好的萦绕展示
    @SpirePatch(clz = AbstractCard.class,method = SpirePatch.CLASS)
    public static class LingerField{
        public static SpireField<Boolean> lingerPlayed = new SpireField<Boolean>(()->false);
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "useCard")
    public static class UseCardPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _inst, AbstractCard c, AbstractMonster monster, int energyOnUs){
            LingerField.lingerPlayed.set(c,true);
        }
    }
}
