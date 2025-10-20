package Theresa.card.skill;

import Theresa.action.RandomSilkAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LittleTailor extends AbstractTheresaCard {
    public static final String ID = "theresa:LittleTailor";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public LittleTailor() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseBlock = block = 8;
        SilkPatch.setSilkForPreview(this,new NormalSilk());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new RandomSilkAction(new NormalSilk(),false,false));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}




