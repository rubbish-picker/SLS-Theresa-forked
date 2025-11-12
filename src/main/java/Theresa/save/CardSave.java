package Theresa.save;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

public class CardSave {
    public String cardID;
    public int upgradeTimes;

    public static AbstractCard toCard(CardSave cardSave){
        AbstractCard c = CardLibrary.getCard(cardSave.cardID).makeCopy();
        for(int i =0;i<cardSave.upgradeTimes;i++)
            c.upgrade();
        return c;
    }

    public static CardSave toSave(AbstractCard card){
        if(card!=null){
            CardSave save = new CardSave();
            save.cardID = card.cardID;
            save.upgradeTimes = card.timesUpgraded;
            return save;
        }
        return null;
    }
}
