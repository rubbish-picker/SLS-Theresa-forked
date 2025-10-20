package Theresa.card.power;

import Theresa.action.IncreaseMaxDustAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DustWithLight extends AbstractTheresaCard {
    public static final String ID = "theresa:DustWithLight";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DustWithLight() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.RARE,CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new IncreaseMaxDustAction(magicNumber));
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}





