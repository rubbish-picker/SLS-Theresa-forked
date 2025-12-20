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
        baseBlock = block = 7;
        SilkPatch.setSilkForPreview(this,new NormalSilk());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int randomBlock = com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng.random(0, this.block*2);
        addToBot(new GainBlockAction(abstractPlayer, randomBlock));
        addToBot(new RandomSilkAction(new NormalSilk(),false,true));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}






