package Theresa.card.skill;

import Theresa.action.TriggerDustSilkAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheStrategist extends AbstractTheresaCard {
    public static final String ID = "theresa:TheStrategist";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheStrategist() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new TriggerDustSilkAction());
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            SilkPatch.setSilkWithoutTrigger(this,new NormalSilk());
        }
    }
}






