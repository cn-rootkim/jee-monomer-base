package net.rootkim.baseservice.domain.bo;

import lombok.Getter;
import lombok.Setter;
import net.rootkim.baseservice.domain.po.SysDictionaries;

import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/21
 */
@Getter
@Setter
public class SysDictionariesBO extends SysDictionaries {

    private List<SysDictionaries> childList;
}
