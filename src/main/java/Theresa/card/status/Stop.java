package Theresa.card.status;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Stop extends AbstractTheresaCard {
    public static final String ID = "theresa:Stop";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Stop() {
        super(ID,cardStrings.NAME,-2,cardStrings.DESCRIPTION,CardType.STATUS,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        //this.isEthereal = true;
        SilkPatch.setSilkWithoutTrigger(this,new NormalSilk());
        SilkPatch.SilkCardField.silkTriggerTimes.set(this,magicNumber);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            SilkPatch.SilkCardField.silkTriggerTimes.set(this,magicNumber);
        }
    }
}





