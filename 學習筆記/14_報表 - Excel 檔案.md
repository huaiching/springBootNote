# 報表 - Excel 檔案

使用 `JXLS 2` 這個套件，來產生 Excel 檔案。

此套件 源自於 `apache.poi`，還可以透過 `樣版檔` 來達成 快速產生 Excel 檔案。

其運作流程為

1. 設定 樣版檔
   
   - 樣板變數 `${ }`。
   
   - 透過 Excel 的 註解 來 撰寫 jxls code。
   
   - 支援 Excel 的公式 與 設定。
   
   不同類型的樣版設定方式，請詳 `範例`。

2. Java 設定 資料內容
   
   - 宣告 `Context context = new Context();`
   
   - 使用 `context.putVar(..., ...);` 設定資料內容
     
     - **參數 1**：設定 `樣版檔 變數名稱`。
     
     - **參數 2**：設定 `顯示的數值`。

3. 將 Java 資料內容 套入 樣版檔 中，即可產生 Excel 檔案。

![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAhwAAAH+CAIAAABY+EA/AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAFiUAABYlAUlSJPAAADUYSURBVHhe7Z1PbCTXfefroI0YWRn3eiSjlYy8LdnZITyi0RkoWs5kAnW8swyhEA4NSBHhv+0dmKABwiGMRcKN4enDGtuYBAjnlL4IaMDAgAYMuHMQRogvrRtv5rEPc+Cxj9xbH7moel2vX72q6u5iV9WrevX54AuZrK5/pN+8D3/v1R/nCgAs4uLiQl8EkCOOvgAAyozj8I8aTEL7A7AKpAJmof0BWAVSAbPQ/gCsAqmAWWh/AFaBVMAstD8Aq0AqYBbaH4BVIBUwC+0PwCqQCpiF9gdgFUgFzEL7A7AKpAJmof0BWAVSAbPQ/gCsAqmAWWh/AFaBVMAstD8Aq0AqYBbaH4BVIBUwC+0PwCqQCpiF9gdgFUgFzEL7A7AKpAJmof0BWAVSAbPQ/gCsAqmAWWh/AFaBVMAstD8Aq0AqYBbaH4BVIBUwC+0PwCqQCpiF9gdgFUgFzEL7A7AKpAJmof0BWAVSAbPQ/gCsAqmAWWh/AFaBVMAstD8Aq0AqYBbaH4BVIBUwC+0PwCqQShUYjUZDn8Fg0AkxGAzkCqPRSN8+S2h/AFaBVGzi8vJyOBx2u929jz5ovvOW47P+9o3WuzWR3dYXO21Hy27ri3KF9bdvyA2b77y199EH3W53OBxeXl7qx0sD2h+AVSCVUjMajU5PTzudTutBs/7lG7UbL7ferR1/96XTx875J87V56vm/BPn9LFz/N2XWu/Wajdern/5RutBs9PpnJ6eplXQ0P4ArAKplI7Ly8t+v7/30QdrL7+0/vaNva0bnbYzfOqMf6srIfWMf+seqNN29rZurL99Y+3ll/Y++qDf769SxND+AKwCqZSF8Xjc6/W2H27Wbrzc/ptXTx87k9/pnX7OmfzOrWPaf/Nq7cbL2w83e73eeDzWz3sRtD8Aq0AqBWc0Gp2cnDTfeav++isH337ls3/We/aC5LN/dg6+/Ur99Vea77x1cnKy/OAY7Q/AKpBKMbm8vOz84h/X//SP19++cfR3L6cyQZJPzj9xjv7u5fW3b6z/6R93/+//WTgyRvsDsAqkUjQuLi4O9n9Qf/0Lnf+5NvqV3mWXKKNfOcffW6u//oWD/R9cXFzoP6cP7Q/AKpBKcTg7O9v76IPGn7za+5n5+ZK0Mvmd0/uZ0/iTV/c++uDs7Ez/mZEKQNlZX1+XdyGEqdfr+gaQPYPBoPWgufmN2uljvVO2JqePnc1v1FoPmoPBQP3ZkQpAuel0OrpJFA4ODvQNIDMmk0m/32+8+dpu64vDp3ovbGWGT90bLRtvvtbv9yeTCVIBKD2j0Ug3icJwONQ3gMxoPbjr/s6roRM1w6duY2s9uItUAGwgbgSMsa+cabz5WnffadSd3QdVUYtbqTxwf2T3B3/zNaQCYANxI2CMfeVM483XLn7tdrWDXzqtprP5dXfiIdwR2xF3TuXr7o85+KX77cWvkQqALcSNgDH2lTNSKiJn/+bsfdP9K97Cq7/q7o929m+z5UgFwCrCI2CMfeWPJhXZ2x58y6l/yX3EVunvU/mO+4McfMv9obRPkQqAVYRHwBj7yp9IqYhcfupKZf0rbo4+TOeRw/nEvaP+w+mZd/fdHyS8DlIBsI3wCBhjX/kzRyoyo185J4dO82vTP/kL/ewvr8Bqfs094YU1FlIBsA11BIyxLyMsIxWZ8W/dyYnt95zaq0572533Nj7vMn1K8bZ7Stvvuae3/OP3kQqAbagjYIx9GSGRVGQuP3X6x+6899ofuENMe990B8ryfp/KN91Dr/2B+0X/OHaMa06QCoBtqCNgjH0Z4XpSUTP6lVsrdNrupbr1L7kVQ6vpTo+n/ObH77i7rb3qHqLVdA93+njxANf8IBUACxEjYIx9mWJ1qWi5/NStJLr7bgHR/Jr8m2F6g4hI+AX1InKFza/PNmx+zd1Vd9/d7TXKkTlBKgAWIkbAGPsyRepSicvZv7lWEAnrRESuoN5Nkl2QCoCFiBEwxr5MkZtUChikAmAnrVZLXwR5gVSQCsBSDAaD2bA0pIT2Hg4LQCpIBWApOp1Oe/2rn+9ukbTSXv9qp9PRf9ElB6kgFYClQCqpB6lYFqQCkACkknqQimVBKgAJQCqpB6lYFqQCkACkknqQimVBKgAJQCqpB6lYFqQCkACkknqQimVBKgAJQCqpB6lYFqQCkACkknqQimVBKgAJQCqpB6lYFqQCkACkknqQimVBKgAJQCqpB6lYFqQCkACkknqQimVBKgAJQCqpB6lYFqQCkACkknqQimVBKgAJQCqpB6lYFqQCkACkknqQimVBKgAJQCqpB6lYFqQCkACkknqQimVBKgAJKI5UnjRuPQktTC8PDm/WDh+Gl299fu/WnY0H+sIVglTSytM7ztNn+kI3T5z7h6GFS26bPEgFIAFpS+XujhNF425ozWDu3fLWeXB4U9/UY75v7u7cvP1MXzjNs42a5wxfKg9v33FXvrsj94lUliB1qezfcV6EFoq8OJw6YyqGZ859b+V9x3ku1llCKu5WO6GF1wpSAUhA2lJx82yjtnMv4uv43N1xRBkRWU88OLw5k8qzjdrMNZ6rAkskUjNTi/h7lgp5ePuO8ApSWYJVpPLiUPn/xevoA0skUjO+RaZSkQp55twXXglJJXqHIa5XuyAVgATkIpWI+kPpx91P/fUj1vSIr1Qe3r4TKoP86kTus3b4cCqVJw15LE9mjbvGpTIYDIbDob40Y3q93mg00pfGs4pU9ETVELI6ERE6Ef99vuPsP5l9tL8TLZVw7TKnGEoUpAKQgIykogrB7cSDXb9mHdGnP2nMuv7gDgOVSjCzUSz3oOIQ05G02WqeSMSelVEvGUNSGQwG7Xa7VnN/V8usny6NRsNxnPX19U6ns4xdUpSKHMVyywthlyf+F36ESIRUZqNeMjFSeXpHbXchrusYpAKQgIykEhr+Ckx7RIyJTQejFlYqwRX8fT5puENh7n+nw2jhROrKS75SUV0imbN+RgipSBbaZRWpBPp6v2d/vuOKxP1v/KhU7GR7jFS01ahUAAyQl1QC1Ub4Qq/gqNTymV4XoGwrrTM9hGeaIK5+FMHkIpVIl0jC62eNJhVJnF1WkYqWfe9A6oiWtI6oSIRpAnj6mQkmTipP9O1U9HJn6SAVgATkJRUxuiUWhi7WundLOCB6yl0QHNGat2b0lWBapZLT1V/zXSIpjlQkml1Wl8q8ufSYkkKrVOZc/fXi0LNUcBhNrVSe3kEqALmQkVTUHmMqGNl3a524O/BVuxM7axI9bBUxgCb3FpKKN21zV59TuXdrqp8MpPLxxx8v4xJJo9Fo5cva2pp+EjEIu/xJ/T+vLhW1OpnFv9ZLW1nMkQTmVJ74+pkjlXiQCkAeZCSVcKUii4PA0NN0WEz+V+8IFALWSVSpqJcAqONs0zPJQCo///nPT09P9/b2luy7f/jDHw7zpV6v6ycRRb1ePzg4GA6HOVcqYrArfPXXdBAsJBW5nEoFwDA5SsWrGBq3wlcAz72+a/VKRchMuflROwF38G3ODH/iqMNfk8lkGbsUbfir0WgIl8zWT0Mqy1cqojqZ3fyoXYLsVyRycGzqDCoVAONkJZWG6KldgrPokX23IhW904/eJEGlMrtXP7AfdQ+pVyphScy3S3j9rImUSqPRODo6Oj8/19dOSSqxaFLxCw5tTkXdg1apRF7oFbnwGkEqAAlIWyr+EJYvBqWkcD9yK5XwnSJapRIYj9JlENptMHqlIjdX9uOVJrNDZDD8NUcSkXaZs35GqFKZ4xJJKlJZslKRLplJxStBZiLRhr9EKTO3TBFEX6C8KEgFIAFpS0XP7D4V2Y97M/NBT+jDX8q8S6xU9A5Dog9/zduPm3ylIlHtssz66dLwWOgSSSpSiSWmpFjyPhXtlnsZKhUAA+QiFfVBLF4i6olQ+eK6xyMoCf2+k9lYWfR9kcqnxZKKZDKZXFxc6EszJnwnynxWkYp+34kyQRJ5X6T66TJSiZNH3PKkQSoACchaKhVMUqmUglWkUvYgFYAEIJXUg1QsC1IBSABSST1IxbIgFYAEIJXUg1QsC1IBSABSST1IxbIgFYAEIJXUg1QsC1IBSABSST1IxbIgFYAEIJXUg1QsC1IBSABSST1IxbIgFYAEIJXUg1QsC1IBSABSST1IxbIgFYAEIJXUg1QsC1IBSABSST1IxbIgFYAEIJXUg1QsC1IBSABSST1IxbIgFYAEIJXUg1QsC1IBSABSST1IxbIgFYAEIJXUg1QsC1IBSABSST1IxbIgFYAEIJXUg1QsC1IBSABSST1IxbIgFYAEdDqd5mtfaq9/tfj5VuNWeGEB03ztS0jFpiAVgARcXFx0SoLjOPqionJ+fq7/oksOUkEqALbhOPyjNgZSQSoAtoFUDIJUkAqAbSAVgzQ33u786KXLT/UO1/pcfup0fvRSc+NtpAJgG0jFIOPx+Pgfjuqv/9HRx1+oSMly8Wvn6OMv1F//o+N/OBqPx0gFwDaQinEmk8nJv/5L483X9rZunH+i98LW5PwTZ2/rRuPN107+9V8mk4n88Wl/AFaBVIrD6elp8523Wu/WBr/Ue+RSZ/BLp/VurfnOW6enp/rPjFQALAOpFI3hcLi781frb9/oH7tzD+E+uiy5/NTpHzvrb9/Y3fmr4XCo/5w+tD8Aq0AqxWQ0GrW//2Htxh/utr7Y+5kz/q3eZRc24986vZ85u60v1m78Yfv7H45GI/1nC0L7A7AKpFJwBoPBwf4P6l++sfmN2smhO9Ed7seLkItfOyeHzuY3avUv3zjY/8FgMNB/khhofwBWgVTKwtnZ2dFPf9J487X1t290fvRSQab0zz9xLw5ef9udgT/66U/Ozs70814E7Q/AKpBK6RiNRp3HP2++81b99Vda79Y6bef0sdu5h3v8LHL+iXu4Ttude6+//krznbc6j3++cIxrDrQ/AKtAKuVlPB4Ph8NOp7P30QfNd95yHLejP/j2KyeHzvCpM/md7oNrZPI7d1cnh87Bt12BOY7TfOetvY8+6HQ6w+FQ3GiyIrQ/AKtAKjYxHA57vd7RT3/SetBce/klx3EnOVrvTnP83Zc6bbfI6LRdVcjIhcfffUmuvPkNVyFrL7/UetA8+ulPer3enCu4VoH2B2AVSMVuzs7Ohj7dblc+8rn1oCkjF3a7XbnyNWZHrgftD8AqkAqYhfYHYBVIBcxC+wOwCqQCZqH9AVgFUgGz0P4ArAKpgFlofwBWgVTALLQ/AKtAKmAW2h+AVSAVMAvtD8AqkAqYhfYHYBVIBcxC+wOwCqQCZqH9AVgFUgGz0P4ArAKpgFlofwBWgVTALLQ/AKtAKmAW2h+AVSAVMAvtD8AqkAqYhfYHYBVIBcxC+wOwCqQCZqH9AVgFUgGz0P4ArAKpgFlofwBWgVTALLQ/AKtAKmAW2h+AVSAVMAvtD8AqkAqYhfYHYBVIBcxC+wOwCqQCZqH9AVgFUgGz0P4ArAKpgFlofwBWgVTALLQ/gHLT6/U6Co7jqN92u119A4AsQSoA5ebk5MSJ5+joSN8AIEuQCkC5mUwm9Xpdl4nH2traeDzWNwDIEqQCUHriihXKFMgfpAJQeiKLFcoUMAJSAbCBcLFCmQJGQCoANqAVK5QpYAqkAmAJarFCmQKmQCoAliCLFcoUMAhSAbAHUaxQpoBBkAqAPUwmk0ajQZkCBkEqYBu/+Kf/LacWoGoc7u/rDQLyBamAbXx/7+Pju+98vrtFqpanD9598Ofv6g0C8gWpgG0glcoGqRQBpAK2gVQqG6RSBJAK2AZSqWyQShFAKmAbSKWyQSpFAKmAbSCVygapFAGkAraBVCobpFIEkArYBlKpbJBKEUAqYBtWS+XB4c3a4cPw8q3P7926s/FAX1ixIJUigFTANkorlbs7N28/0xdO82yj5jnDl8rD23fcle/uOLeeiHXmSeXB4U33bvP4FaJy75YTfz7FDFIpAkgFbKMUUnm2UZs9WqRxV18ikd361CK+VKRCHt6+I7wyType5q/g7mcx8/ZQgCCVIoBUwDZKIRU9D2/f8dSixq9OxLdCJ1OpPGk4O/fkmnd3GncXOGMZqYROQEvwfIoYpFIEkArYRgmlMhvFcusV0bnfuzX9wo8nEiEVZdRLRnPGUpWHMj0TkMqDw5ti/964mb8cqcAyIBWwjTJIZTrJMcUf43rScHtw979qd69vGPNRWCrBGRFXV8ElTxqRu7q7Mz26P8dz71b8+RQrSKUIIBWwjTJIRcvdHU8uyoiWtM60IvFME8TVj9LXL5CKW3zsBC0Sloo3rzOdoXGc2p2bchLFPZ+ClylIpSAgFbCNEkklenJeEH3llVapxF/9pUlFfBocUgtKRWrDM5wy5CXFxvAXLANSAdsol1SU6kSJXmdMV76zcVefU5EX/s6Tymx9dYY/VKmI8ig0WzMtpPSypoBBKkUAqYBtlEsqeoEiCUnFc0DE1V9TN8RLRb9UzNeDIhWhE3e8K5abt3ailVOgIJUigFTANsollaUrFVFtKDc/alcAu7MgSj3h7+FJIzQX4l4YJsykFx/hJX7m3ZhZnCCVIoBUwDbKJRW9IJBonfh0OkS/+kvdQ0SlEndviueksELCS/wgFVgWpAK2US6pLFepSJcoUvFKk5kz4oe/4hJWSHiJH6QCy4JUwDbKJZVAdaIS3Ynrlcos6UhFP4sZi/ZWhCCVIoBUwDZKIRW9+w7cza4s17vyFKSimEyfdQ9rxg+VCiwLUgHbKIVUSBZBKkUAqYBtIJXKBqkUAaQCtoFUKhukUgSQCtgGUqlskEoRQCpgG0ilskEqRQCpgG0glcoGqRQBpAK2gVQqG6RSBJAK2AZSqWyQShFAKmAbSKWyQSpFAKmAbSCVygapFAGkAraBVLKKfCFYHnHfDBb9tM34IJUigFTANpCKFvc5YyEZuAu1N7IsiPtQsqS9/Cpxn1GW7AyRSiFAKmAbBqWSvKeeZZVtwwnszX1IvvakyOR1gPtqL/0BlNkm+RGRShFAKmAbSCW0N1chgWcYu5pJ1l+7dUOo3Mk4ic2HVIoAUgHbuJZUlAfORz+FXnbBYhTI7e88ZAUQfGT9dCdRewjUDaLfjNxWPbfa4cPoI8763JknIvamjYAFlRN1kuJV9vdu3/GPmK7zlkzEu5DnBqkUAaQCtpFcKl6v6veYTxqip9YXBrvsac++uLP2v1X/0pdbqevH99qBI3pvQ5mdSZRUovbmv5fe+1atAAI7UbYSDguoNFH/nkr0n2JRkEoRQCpgG4mlEuhw/egDRLIjXror13fr/e2vdOt3bko9hLYNJHjEa5yJv5OpFfQfbRbFfNrQU2gALZckHXNDKkUAqYBtJJZKZCerL0zelbtS0ZEbitcvqt10SAMyqUhl1kHrY0raeSIVWA2kAraRWCp6SeElHamEdjv7yNlpBC7KCmvAT/CIs90ufSaBDdWCyT8TVXXRUmH4C5YFqYBtJJZKcPLDn1PxJhXUCY9pb7uoK5/9ZR3YbeTh1PUjtlWOGDWnova52hSItrfZPt0xN3V5QCreTqKlovXv6unN+Sn0b6M2j/t6zi8wNkilCCAVsI3kUpGdssesF1MWBnu6OKnMhpKmOxE+kLhrqlYIDCsFttW72qjrzdQzvPVEu6tDPxM34TE3uXC65w15z7wuleBIVD5S0c9hYZBKEUAqYBvXkkqRE9SYqWjSWirBobakSX5EpFIEkArYBlLJJolP49lGbZVpGB7TUlKQCtgGUskqPFASlgCpgG1YJxWybJBKEUAqYBtIpbJBKkUAqYBtIJXKBqkUAaQCtoFUKhukUgSQCtgGUqlskEoRQCpgG0ilskEqRQCpgG0glcoGqRQBpAK2gVQqG6RSBJAK2Mb39z5+8MaX2+tfrWa+1bgVXliRbH/lj5GKcZAK2MZwOOxUGMdx9EVVYjgc6g0C8gWpAFiF4/CPGkxC+wOwCqQCZqH9AVgFUgGz0P4ArAKpgFlofwBWgVTALLQ/AKtAKmAW2h+AVSAVMAvtD8AqkAqYhfYHYBVIBcxC+wOwCqQCZqH9AVgFUgGz0P4ArAKpgFlofwBWgVTALLQ/AKtAKmAW2h+AVSAVMAvtD8AqkAqYhfYHYBVIBcxC+wOwCqQCZqH9AVgFUgGz0P4ArAKpgFlofwBWgVTALLQ/AKtAKmAW2h+AVSAVMAvtD8AqkAqYhfYHYBVIBcxC+wOwCqQCZqH9AVgFUgGz0P4ArAKpgFlofwBWgVTALLQ/Qzzfd5z95/rSSJ7v33/6Ql+07MZ5cb2TfPH0/sJ1IBlIBcxC+zNAsCt9vu+E+mMXdy2d6VZLdNfXJ6CH4Mm9eHpfO+4qJ+l6NYKIX4a3ZsRycfSID6oMUgGz0P5yJqoX9gh1jbIH93t5ZcGC7noFnu+7u3aPsB/o8+8/ffHi6b57HmKNKdc9SffXEPqJQ4WL1M7MUwHCewCkAoah/RUTvfuccX9/P6ClNDtWedTwTmcyDNRYMcw7SW9P9++HpRJXskWMrEE8SAXMQvvLEa873X/u/m/ECJM+thRTBCiTMfoW025+ToGwDLIPV5whl+jde8KTdJfLXQQkEmeUwFFl9aOsHfxlAlIBw9D+ciI87OV2haGlwZGl4Gfux/vqoFJQKvpY0bWYdtvTkS4VOTSlD39ppzj/JINMhRA9FhbaubfK9Oi+SuYPslUSpAJmof0ZQ0hA60yVHjumCFD+clcLh2kNkOCisggC41n7T2d9+v2nTwMfhZWx1EmGEWNhc1YQJ6X/SPNMVXWQCpiF9pc7fs8tesXpX+MR/WpkESD6a/G/UT1rVA88JWoGRF/VdZpbpTwP79qVg7c4YIlrnaRAbBrz4QzPlvdFXRJ1tMV7qBhIBcxC+8sHpTtUOsHnz/2vZ5/rfeRsyuXp9JPZ0E9YRXOksgTiGIpZ/HN2J97d3XqFVMRxE51kpBoU1PN3z+OpuwtvP9JQ4S/AB6mAWWh/RvDrk/t+H6wTVVYIxES//G/EduGlSzJTgueE/f3pxErEDItH4pOcjfhFuEYQ2ECYSazrnlykjq7941oKUgGz0P7yRHaK2gBSXL84m4YOTki7Pe/TKKfMk0qUAiJXjVoxQNAHyU/SW3UJqcgbY/x1wwVKpFmrDVIBs9D+jDHtu72/wl+Iv8QDnyuzLaHrbb3PonrTOVJZBu+cYo8YseNrneSyUpniVyoRVYqPvkmVQSpgFtpfngQ6xllHOJ2y3pczLCrCPcFeczaMpLOiVFSCvXjUwWYkO8lrSSX6w/CSyoNUwCy0P4P43XZM/+rLxrtvRN7JIf/+V5ZFjVnF7XRJ/P25c/ayGIkiyUnOCJ2ugm4JpJIIpAJmof3liTaEE9Xb+ni9rtpfepPW7gVZ6sJ07/1TTy+0W/VD/8Svf5IpVCoLZFdZkAqYhfYHUG56vV5HwXEc9dtut6tvAJAlSAWg3JycnMgaMszR0ZG+AUCWIBWAcjOZTOr1ui4Tj7W1tfF4rG8AkCVIBaD0xBUrlCmQP0gFoPREFiuUKWAEpAJgA+FihTIFjIBUAGxAK1YoU8AUSKXwJLhJPuLuj6ibRLIj0QmE3tkYcY8KJEAtVihTwBRIpdC4N/jN+tnQ07WmaPdUeky3iu/S0+RaJxBWSISSIAGyWKFMAYMglcIS1VN7hHpe2T37vbKyIK5LT5XrnID4ZN7jWkI/JyxEFCuUKWAQpFJ24rtl981age+z6aSvdwLhqos6JQUmk0mj0aBMAYO4Uvnwez9U/+nD6vynl9dGo5H+y14e/4GMwWmH2fsVgwNHMYWCMhkT9dAsj5gyIiEJT2B6CnLVwK9uygon9o+/+Cd9b1AZPvzent4gIF9cqTT/4n3nl586//7/SFqp/dlfDodD/Ze9HOFe1u19Q0uVXjf0mfcgfXXgSenTlQEppWtfjUQnINyzvz/VpW5Il6hly/Ph9/acn951/n2XVC6/fND8iz/XGwTkC1LJJKtIJUzkm0m818ULYgoFZUQpbmgpwZVl80h2At6HsgaLEkjUsuVBKtUNUikASCWTpCOV2TtN3O/iH/UeWSiIPl38b2wnvUAqUdMlUasnOgFhQ1Uq+qYuUYdZEqRS3SCVAoBUMskKUlE6WaVjfS7fCjn7XO93Z1Mu/ouJ/Z47plDxZ27SIskJUKmQDIJUCgBSySQrSEXDr0/ua2+wl0RVEwIx0S//q5OWUq53AlQqJIMglQKAVDLJylKRXa3yF767LK6vnU2/B+8Mcbvzp+EuXahArx5CRPkivCuPpCdApUIyCFIpAEglk6wslQDTvt3thF+8cC8E06oWZbYldPtHZD3i7VBfuAKJT4BKhWQSpFIAkEomWVkqga521sGKxfv7coZFRbgn2BtHXDiWtlFmLHkCHlQqJIMglQKAVDLJylLR8B0T7pwFU9mIq6r8tWSNoCyLKgzidpqE5U9gtgFSIWkHqRQApJJJVpaK1vfP6/dDlYfXXz/XJmDiH8K1Mtc6AbnOHOb91HNAKtUNUikASCWTrCwVuD5IpbpBKgUgd6k8es957H/d6zpbv4n+SORx23n0++kXYs0t/a9Z53Y3sMmWM93J47b/0W/8L37v3G4HVs4sSMUgSKW6QSoFwKhUNG2IL9RESkUTjxZ1hV7Xcdq+VH7jOO85vdD62QSpGMRyqWw5ztY994vHDce56fTkRw+d2/5Hj246tzf0DXsbsz/FHod2a0eQSgEwJ5XHbbXemKHWLteXym/03Uq0yiabIBWDVEUqwiJSHq5jGtOvI6Ryz238jx76G/prWhakUgByk8rv3X8AM9RhqPhRqURSUUfGHnWj5dGLWZ52kIpBKiMVv/hwyw7VGVFScddUyxpLg1QKQG5SEfHUMrVCTDGhDY5JoudUtBEtuX9vyOvRe9rasbJJO0jFIBWSivCH09AtEpaKsI66oZVBKgUgX6m4kxxambJcpSKz9Z4bKZ6toFRm+/eloipqC6lUgmpJRdhCmyaJkIoYHxN/WvkFjX1BKgUgX6k8arsaED27XnNIFMFESuWxvGYsZKNHbbdSeeSvEK5U4obO0k6xpRJxL+JSzO5wLDRIJVoq8iMX5lQgK/KUilI9hK8e3oqaQletINbf8qqQqXjktcLK/sWky6N2bhd6RabIUkmsFOVGTO/2+BcvIm7Lv/69iqlTLamIb8UgmFw4RypyJsbKegWpFIAcpaLpJFBGiFGs3zu3g8NZ6sruF35pIsyh1TFiHTmTH1cJ5VKsFEQqUU8ZXoSqBn97XReuVLSCJfQgSXNUSCqzq4qV64kXSkWsjFQgG3KTyu+dLa+qCNcowiWPvDl5vXzxHTPdyi9NxF2TgQkVf/+qVKZ7UwqaiKNnkoJIJYT2wPtFz7+PMwVSMRbVB0GRqLethKXy6Gb0mpYFqRSA3KTiZ1Zz+H8ab3W9r8Nz9cqUidiq152VJu7fa8odLTJUKjHETIiIxTE+iDMFUjEQf+5EvRklMDWi3LYynTjxETqZ/Yuw1ChIpRiYkooft9rwKpXHijBE1Ie4TIfO/JkS9xEsbX2sbLrD8lUqo9Go0+mcn5/rH6TE8oNguhbiTIFUSDGDVAqAQanIe0r8qZRH7wUm3tX7HN2tlAEu+VAv/T6VMlUqwiXr6+vivBaun5zpfLrX/ctHzccTrmXiTIFUSDGDVAqAKan4PhDT9dIlszveg1d2PfIuRBbz8IGCJnRVcUSlEj565pkjFc0lkrj1UyL6gi1JaFjMI6LG8cyBVEgxg1QKQO5SqUbCUolziSR7qSTv9ONMgVRIMYNUCgBSySRSKgtdIkEqaYFUqhukUgCQSiap/dlf/vjHP17GJZJms9lKlc8++0z5P/q6w1+RppgrlaOjI/1U8uX1N99AKhUNUikASCWT1P7sL//jP/7j9PR0b29vbW1N78KjODk5GabKeDxW/o/Or1I5Pz/XTyVf3v/r/45UKhqkUgCQSiZR51Qmk8kydmH4Ky0Y/qpukEoBQCqZJDxRv9Au4fXTYMGoV4igKkKm8C4H23+OVFaMfGBwHJHPUJm+PcW7yVFniQdEPrrpPnTS7herIJUCgFQySaRUJJF2mbN+SsQ8lGVODSNN4btpahIpFdVZcTvJnbylor6mdw7qU4QfN+a92uRxIyAVeXv89Mkr4fc2hpdERUhlur6lXkEqBaAYUnHvkA89olhN3ENZYuO9ASyXW1IiM18qEtUuy6y/Am73H9fte2qI+nB2n0qwLomoVApE3lIJR7NCONeoVGYeCiskvCQqM6nMzeOG/tywRJk979J7rswyR0wxSKUAFEEq6usgY5JYKt59jkk3SS9LSkUymUwuLy/1pakRU6Pog2OBVWJNg1QWZhmpLF+p6JuEFaIuiRwciyTSQ97miU2gPNpSfYiy+hTLfIJUCkABpOK+rjH8NMlgriGVZXabWZJKJUvmTasEzLD8tAhSmZ9IK2grzGe2eUgSj+7pS1wiDRE66Pyzmg7lLbEr/UVhMVJZcm8pBqkUgAJIRXvkV2SuIRWjI2BFkkrlKIdU5vwJH7m5O7Oy2kRI5G61hJ+ZHxldKnEf5T4ChlQKQAGkogrjcVt5RqRiBbmOu4KPUFHcJmIr7cnHeQWpGKQcUpmPvvlDZ+umO7se0ZX7j8SfU9yISf7eRtTmwQSUEBwKc8+5oe9criw31Hzj/huc+6tIN0ilABiXijehonb98iHEqmzE1+6IVuixxHGbhL/NMUjFIOWQypz+Pbz54w3nkbfJ1k33aw3R7/dCR+xtBMoO7duIaG+EjJSK93W4UpkjlTk/aepBKgXAuFS82iJQT3hLbr8XmBGZ6iG8cvwmSw6sZROkYhDrpPLQ2dqYbtLbUEaTwjP2wegWubdIKl7Rk65UlhxPSytIpQAUUCr+8/C18mVac3jru4TeZh+5H6RSPYotlaixqWiUdwM/jvRQUqksvD0FqUAKGJdKaPjLHeNynC11piRqIEudSoncJHKrvIJUDFJsqSSNV6aoxU3cfEz4iFOpiEkXTyfuS4b8T7fCQmL4C1LAuFS0rt9zjPh29obHKD3M5ldiNlGX5x6kYpDySCXYa4s78+M2TFqpzNyjrDOrXWKGwmKVIMykSEXbPFoqyqXG+QSpFIACSEUdpHJHseS8iDIyJqSiXvolr/KK24RLiqtKGaQiBsGiBqPEQ1nCPf7yUhFyCu9BRFyOFXeG+miVf2mZ0wjcdCKfTCNPKVoqXFJcRQoglYzuUsxot8sFqRgkX6kknyPZitGJmvBtg1OpyF4+TJRgIuLtIU454eOuknT3tkyQSgEogFSWeUzLNVKqx7RAiuQrFcsSHJFbMTympZIUQSpLPFAycUyOfSEVsyCVlbLiAyVnyX3sC6kUg2JIxbogFYMgleoGqRQApJJJkIpBkEp1g1QKAFLJJEjFIEilukEqBQCpZBKkYhCkUt0glQKAVDIJUjEIUqlukEoBQCqZBKkYBKlUN0ilACCVTIJUDIJUqhukUgCQSiZBKgZBKtUNUikASCWTIBWDIJXqBqkUAF8q3/yOs3dc9Hz0v5z/8QN9YSGz9sZ/QSqmcKXy395w9tZJ5fLNryAV47hSGQ6HnTLw93//97VaTV9aSLrd7mQy0X/ZkAvn5+f6/x9QGfhjzjiuVMrCxcVFo9HQlwIAQGEok1Tc03VKdsIAAJWiZH00UgEAKDIl66MbjcbFxYW+FAAAikHJpLK7uzsYDPSlAABQDEomlePj4263qy8FAIBiUDKpDAaD3d1dfSkAABSDkkmFq4oBAIpMyaTi3v/fbJ6fn+tLAQCgAJRPKuK+WX0pAAAUgPJJ5ezsbHNzU18KAAAFoHxSubq6qtfr4/FYXwoAAKYppVS63e7x8bG+FAAATFNKqYzH40ajwWOAAQCKRimlcnV11W63+/2+vhQAAIxSVqmcn583m019KQAAGKWsUqFYAQAoICWWCjMrAABFo8RS4fmSAABFo9xSmUwm6+vrvGEFAKAglFsqV1dXw+Gw1WrpSwEAwASll8rV1dXBwUGv19OXAgBA7tggFQbBAAAKgg1SEbetbG5uciUYAIBZLJHK1dXV6enp3t6evhQAAHLEHqlcXV0dHR2dnJzoSwEAIC+sksrV1VWr1RoOh/pSAADIBdukcnl52Wq1eN8wAIARbJOK8Mrm5uZoNNI/AACAjLFQKldXVxcXF61Wi4uMAQByxk6pCK80m028AgCQJ9ZKRdYrjIMBAOSGzVIRXtnc3GTeHgAgHyyXirwe7OzsTP8AAADSxn6pSK/w0EkAgKyphFTEQycPPHg+GABAdlRFKoJer9dqtcbjsf4BAACkQbWkcnV1dXZ21mw2mWIBAMiCyknl6upqPB5vbm7ycnsAgNSpolQEx8fHDIUBAKRLdaUi3m/fbDY/++wz/QMAALgWlZaKGArb3t5ut9uXl5f6ZwAAkJCqS0XQ7/fX19cpWQAAVgSpTKFkAQBYHaQSQJQs3HsPAHA9kIrOeDw+ODhoNpu8lhgAIClIJZrz8/NWq7W7u8sbWQAAlgepzGMwGKyvrx8fHzPRAgCwDEhlAZPJpNvtMtECALAMSGUpmGgBAFgGpJIAOdHC8ygBACJBKokZDAabm5utVoubJQEANJDKNRkOh9vb281m8/T0VP8MAKCqIJWVOD8/39vbazQa/X6fd0oCACCVFLi4uGi3241G4+TkhIuPAaDKIJXUGI/HR0dHjUaj0+nwmhbL6Pf7DqTNycmJ/ouG8oNUUuby8rLT6TQajXa7zUVi1tDpdJzNtnP0OUktm+1Op6P/oqH8IJVMmEwm/X5/c3NT3DXJmFjZQSrpB6lYClLJltFodHBwUK/X2+02N06WF6SSfpCKpSCVPBCFS6vVajQa3W6XGZfSgVTSD1KxFKSSKxcXF8fHx/V6fW9vj3snSwRSST9IxVKQihlOT0+3t7fr9frx8fFoNNI/hoKBVNIPUrEUpGKS8XgsHoG8vr7e6XSwS2FBKukHqVgKUikEo9Go0+msr683Go2jo6Pz83N9DTAKUkk/SMVSkEqxuLi4ODk5aTabwi5cMFYQkEr6QSqWglQKirBLq9Wq1+sHBwfM6psFqaQfpGIpSKXojMfjXq+3vb1dq9Xa7fZgMOBWyvwpt1Q2HGfX+2J3x3nj0Fv4xP/imfPGjr6+mvfvOBtP9IWpBKlYClIpDZeXl/1+f3d3t1arbW5uHh8f8xiY3LBEKkefO+1Dx9nxpfLEce44bWVN99MleP+ZfohrBKlYClIpJWdnZ91ud3Nzc21tbXd39+TkhCvHMqXoUtlw5tUTU6k80d0gmVYtnlTU/ag2knn/DlKBOSCVcjOZTAaDwdHRkbhyrN1un56ecsd+6qQmlffvBLvzuUNPyydOKhvKod4/nMlDTTtm+e5OYJ/at6sHqVgKUrGHi4uLfr+/t7dXr9ebzebx8THT+2mRplQie/AVEycVN8+cN/xK5Y3DkNWCstnd0T+NhEoF4kEqdnJ+ft7tdre3tx3H2dzcPDo6Oj09vbi40NeD5SixVKbTJP48yvt3AiNaG5EVzDPnjeBcS1pDXmqQiqUgFfs5Ozs7OTkRrz2u1Wrb29udTmc4HPL+4+XJVipufSA7cW/mY9rve0WGYOoMZYk6dDZHKu/vuJu870+WhCuViFmTHaetHsgjfNorBqlYClKpFpeXl5999lmn02m1Wmtra81ms91u93o97uGfT5pSUZEd+obfa8/04HXrUhXunHlwieqnWKl41YmYb3dVEV4hGO303jh0hSdqlNmFYcEi5tpBKpaCVCrN+fl5v98/ODhoNptra2utVqvT6QwGA64l00hTKtF/8nsFyht3ZvWH24mHr/dVlygXBMdJRQx2yYu41Hl7lXCxIiOlknqQiqUgFZgymUyGw2Gn09nd3V1fX3ccp9Vqtdvuv/zhcFjxK8qyl4pfJcge3B0TC14bFnkTibRFhFSeORt+9SNXm/pD3vzoi0ff1g9SgYQgFYhlOBz2+30xVlav10Upc3BwcHJyMhwOK3Vjf+ZSEcLYUCZX9LokaolMtFSUT5eqVELzKLGkcSU0UrEUpALLIkqZXq93dHTUarVqtVq9Xm+1WsfHx71ez+6Z/4ylokyWyMmVhXMqapaXyrKVin+9wLRS8b6dc4hrBKlYClKB6zMej4fDYbfbPTg4EDP/4gpmYRoxblbMBy33+/3Nzc1er7fksF6aUgn/ye8ulH/7e933dMRJuQc+4uovpWJYXiqRBKQiDurvWR3+EnexzDlQoiAVS0EqkDJnZ2fCNGLcrNVqOY4jhs7E1czdbnc4HJq93qzf78sedXt7u9/vzx/NS00qRhJRqShRK5XAxc3+Em1OJbrYSh6kYilIBfJADJ2Jq5mPj49brVaz2XQcp1arCfGIykb4JgflqFIRrK2t7e3tnZ6e6qt6lFsqxQxSsRSkAia5vLwUFhGVjfCNVI7jOGLaRlwg0PEYDAZik1WmcMJSkYhXDGhPuEEq6QepWApSgUIjpm3EBQJCKru7u0IzYgpHIJZIRN0jkR4SjMfjOVKRiNejiZoJqaQfpGIpSAVsQHWGrHsk0kOCer2uC2QujUbjb//2b5FKykEqloJUoIosU6k4jrO+vt7pdEajEZVK+kEqloJUoIrMl8rm5ubJyYn6UGekkn6QiqUgFagikVLZ3t6Ou3MFqaQfpGIpSAWqiJSKuJLY8vtUihmkYilIBarIYDAQr15e8rrk/KSyuxN4TEv4XsXVM72r37vJUb7qUT6LJYsjRgapWApSAVhMXlJRRZKlVAKPWvEOJG6b1z/KMkjFUpAKwGJykor7HGL5ELCEUpn/+C81ujkUqQROIOMgFUtBKgCLyUkqgcdqmZBKniNgSMVSkArAYnKSSkAMQiryQcXKSx7V5zm6HvLeQi/R9jBFqT/mScXbf0Zv5dKCVCwFqQAsJhepBDv3qRJ8l8jH4wceJKxsolcqwZevqDXQQqksWfGsGKRiKUgFYDG5SEV9k0p4+EsOTGlTIDGvqddfE6m80H6+VNJ6sv3CIBVLQSoAiymSVJR+XxVAhFRCyM2RCmQGUgFYTC5SiRr+klIRkph+KwQTXCFCKjEvtJ8vFYa/YDWQCsBicpFK1ER9eE5FrukSXBKoMIJzKmrmSSV+q9SDVCwFqQAsJiep6JcU33F25ShW6C2/2uvi5XiXriWJb6B5UuGSYlgVpAKwmJyksvy9h3NGtxZmjlSWP4HVg1QsBakALCYnqeiT8/FZZTp9jlT0j7IMUrEUpAKwmLykoj5Qck60Kf2E4YGSkCVIBWAx+UmlOkEqloJUABaDVNIPUrEUpAKwGKSSfpCKpSAVgMUglfSDVCwFqQAsBqmkH6RiKUgFYDFIJf0gFUtBKgCLQSrpB6lYClIBWAxSST9IxVKQCsBikEr6QSqWglQAFuNK5VbT9Urx81+/qS8pZm41kYqVIBWAxVxeXnZKguM4+qKicnFxof+iofwgFQCrcBz+UYNJaH8AVoFUwCy0PwCrQCpgFtofgFUgFTAL7Q/AKpAKmIX2B2AVSAXMQvsDsAqkAmah/QFYBVIBs9D+AKwCqYBZaH8AVoFUwCy0PwCrQCpgFtofgFUgFTAL7Q/AKpAKmIX2B2AVSAXMQvsDsAqkAmah/QFYBVIBs9D+AKwCqYBZaH8AVoFUwCy0PwCrQCpgFtofgFUgFTAL7Q/AKpAKmIX2B2AVSAXMQvsDsAqkAmah/QFYBVIBs9D+AKwCqYBZaH8AVoFUwCy0PwCrQCpgFtofgFUgFTAL7Q/AKpAKmIX2B2AVSAXMQvsDsAqkAmah/QFYBVIBs9D+AKwCqYBZaH8AVoFUwCy0PwCrQCpgFtofgFUgFTAL7Q/AKpAKmIX2B2AVSAXMQvsDsAqkAmah/QGUm1ar5cTTaDT0DQCyBKkAlJter6ebROH4+FjfACBLkApAuRmPx7pJFM7OzvQNALIEqQCUnrgRMMa+IH+QCkDpiRsBY+wL8gepAJSeuBEwxr4gf5AKgA2ER8AY+wIjIBUAGwiPgDH2BUZAKgA2EB4BY+wLjIBUACxBHQFj7AtMgVQALEEdAWPsC0yBVAAsQR0BY+wLTIFUAOxBjIAx9gUGQSoA9iBGwBj7AoMgFQB7ECNgjH2BQZAKgFW02219EUCOIBUAq7i8vNQXAeTI/wcUwL9Pc5DP+QAAAABJRU5ErkJggg==)

---

## 安裝依賴

```xml
        <!-- apache.poi -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>5.2.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.2.3</version>
        </dependency>
        <!-- JXLS -->
        <dependency>
            <groupId>org.jxls</groupId>
            <artifactId>jxls-poi</artifactId>
            <version>2.12.0</version>
            <!-- 排除其自帶的 POI -->
            <exclusions>
                <exclusion>
                    <groupId>org.apache.poi</groupId>
                    <artifactId>*</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

## 工具

- `generateExcel`
  產生 Excel 檔案
  
  - 參數為 `Context` 的方法，針對 一組資料 的套印方法。
  
  - 參數為 `Map<String, Context>` 的方法，針對 多組資料的套印方法。
    ***此方法可用 groupBy 功能取代***
    
    - **key**：該組的 分頁名稱
    
    - **value**：該組的 資料內容

- `mergeExcel`
  Excel 檔案合併 (針對一個工作表的檔案)

```java
package com.example.api.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Excel 匯出工具 (使用 JXLS 2)
 */
public class ExportExcelUtil {

    /**
     * 產生 Excel 檔案
     *
     * @param modelFile 樣版檔案路徑（相對於 classpath，例如 "templates/sample_template.xlsx"）
     * @param context   JXLS Context，包含資料模型與變數（例如 context.putVar("users", userList)）
     * @return 產出的 Excel 檔案資料流（byte[]）
     */
    public static byte[] generateExcel(String modelFile, Context context) {
        // 參數驗證
        if (StringUtils.isEmpty(modelFile)) {
            throw new RuntimeException("樣版路徑 不可空白!!");
        }
        if (context == null) {
            throw new RuntimeException("資料內容 不可空白!!");
        }

        // 產生檔案
        try (
                // 讀取 classpath 下的樣版 Excel 檔案
                InputStream inputStream = new ClassPathResource(modelFile).getInputStream();

                // 建立輸出流，用來儲存產生的 Excel 內容
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            // 處理 Excel 樣版，產生新的 Excel 並寫入 outputStream
            JxlsHelper.getInstance()
                    .setEvaluateFormulas(true) // 啟用 Excel 公式自動計算
                    .processTemplate(inputStream, outputStream, context);

            // 回傳產生好的 byte[]
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Excel 產生失敗，樣版路徑: " + modelFile, e);
        }
    }

    /**
     * 產生 Excel 檔案 (多檔 + 單一資料表)
     *
     * @param modelFile 樣版檔案路徑（相對於 classpath，例如 "templates/sample_template.xlsx"）
     * @param dataList 資料內容 清單 (Map key = 分頁名稱 / Map value = context)
     * @return
     */
    public static byte[] generateExcel(String modelFile, Map<String, Context> dataList) {
        // 參數驗證
        if (StringUtils.isEmpty(modelFile)) {
            throw new RuntimeException("樣版路徑 不可空白!!");
        }
        if (CollectionUtils.isEmpty(dataList)) {
            throw new RuntimeException("資料內容 不可空白!!");
        }

        try (
                Workbook mergedWorkbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {

            int i = 0;
            for (Map.Entry<String, Context> data : dataList.entrySet()) {
                // 取得資料
                String sheetName = data.getKey();
                Context context = data.getValue();
                // 產生檔案
                byte[]  file = generateExcel(modelFile, context);
                // 合併資料
                try (InputStream inputStream = new ByteArrayInputStream(file);
                     Workbook workbook = WorkbookFactory.create(inputStream)) {

                    // 取得第一個工作表
                    Sheet originalSheet = workbook.getSheetAt(0);

                    // 建立新工作表
                    sheetName = sheetName != null ? sheetName : "Sheet" + (i + 1);
                    Sheet newSheet = mergedWorkbook.createSheet(sheetName);

                    // 複製工作表內容
                    copySheet(mergedWorkbook, originalSheet, newSheet);
                }
                // 計數
                i++;
            }

            mergedWorkbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Excel 產生失敗，樣版路徑: " + modelFile, e);
        }
    }

    /**
     * Excel 檔案合併 (針對一個工作表的檔案)
     *
     * @param fileList 資料內容 清單 (Map key = 檔案名稱 / Map value = 檔案資料流)
     * @return
     */
    public static byte[] mergeExcel(Map<String, byte[]> fileList) {
        // 參數驗證
        if (CollectionUtils.isEmpty(fileList)) {
            throw new RuntimeException("檔案清單 不可空白!!");
        }

        try (
                Workbook mergedWorkbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {

            int i = 0;
            for (Map.Entry<String, byte[]> data : fileList.entrySet()) {
                // 取得資料
                String fileName = data.getKey();
                byte[] fileData = data.getValue();
                // 合併資料
                try (InputStream inputStream = new ByteArrayInputStream(fileData);
                     Workbook workbook = WorkbookFactory.create(inputStream)) {

                    // 取得第一個工作表
                    Sheet originalSheet = workbook.getSheetAt(0);

                    // 建立新工作表
                    fileName = fileName != null ? fileName : "Sheet" + (i + 1);
                    Sheet newSheet = mergedWorkbook.createSheet(fileName);

                    // 複製工作表內容
                    copySheet(mergedWorkbook, originalSheet, newSheet);
                }
                // 計數
                i++;
            }

            mergedWorkbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Excel 產生失敗", e);
        }
    }

    /**
     * 複製工作表內容 (包含合併儲存格)
     */
    private static void copySheet(Workbook mergedWorkbook, Sheet originalSheet, Sheet newSheet) {
        // 1. 先處理合併儲存格區域
        copyMergedRegions(originalSheet, newSheet);

        // 2. 複製欄寬設定
        for (int i = 0; i < originalSheet.getRow(0).getLastCellNum(); i++) {
            newSheet.setColumnWidth(i, originalSheet.getColumnWidth(i));
        }

        // 3. 複製每一行
        for (int i = 0; i <= originalSheet.getLastRowNum(); i++) {
            Row originalRow = originalSheet.getRow(i);
            if (originalRow == null) {
                continue;
            }

            Row newRow = newSheet.createRow(i);

            // 4. 複製每個儲存格
            for (int j = 0; j < originalRow.getLastCellNum(); j++) {
                Cell originalCell = originalRow.getCell(j);
                if (originalCell == null) {
                    continue;
                }

                Cell newCell = newRow.createCell(j);
                copyCellValue(originalCell, newCell);
                copyCellStyle(mergedWorkbook, originalCell, newCell);
            }
        }
    }

    /**
     * 複製合併儲存格區域
     */
    private static void copyMergedRegions(Sheet originalSheet, Sheet newSheet) {
        // 取得原始工作表的所有合併區域
        List<CellRangeAddress> mergedRegions = originalSheet.getMergedRegions();

        // 將每個合併區域複製到新工作表
        for (CellRangeAddress mergedRegion : mergedRegions) {
            newSheet.addMergedRegion(mergedRegion);
        }
    }

    /**
     * 複製儲存格值
     */
    private static void copyCellValue(Cell originalCell, Cell newCell) {
        switch (originalCell.getCellType()) {
            case NUMERIC:
                newCell.setCellValue(originalCell.getNumericCellValue());
                break;
            case BOOLEAN:
                newCell.setCellValue(originalCell.getBooleanCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(originalCell.getCellFormula());
                break;
            case BLANK:
                newCell.setBlank();
                break;
            default:
                newCell.setCellValue(originalCell.getStringCellValue());
        }
    }

    /**
     * 複製儲存格樣式
     */
    private static void copyCellStyle(Workbook mergedWorkbook, Cell originalCell, Cell newCell) {
        CellStyle newCellStyle = mergedWorkbook.createCellStyle();
        newCellStyle.cloneStyleFrom(originalCell.getCellStyle());
        newCell.setCellStyle(newCellStyle);
    }
}
```

---

## 範例 - Each 遞迴表格

### 樣版檔

1. 於 `/resources/templates/` 新增 Excel檔案 `sampleEach.xlsx`。

2. 請根據下圖方式，設定樣版檔
   
   - A1 儲存格：設定 掃瞄範圍
     jx:area(lastCell="C5")
     
     - `lastCell="C5`：模板範圍 (A1 ~ C5)
   
   - A2 儲存格：設定 遞迴表格
     jx:each(items="addr" var="a" orderBy="a.clientId DESC, a.addrInd ASC" lastCell="C4")
     
     - `jx:each(...)`：遞迴表格 的語法
       
       - `items="addr"`：Java 端 變數名稱
       
       - `var="a"`：樣版 端 的 變數名稱
       
       - `orderBy="a.clientId DESC, a.addrInd ASC"`：排序
       
       - `lastCell="C4"`：遞迴表格 的 模板範圍 (A2 ~ C4)
   
   - `${addr.size()}`：顯示資料筆數
   
   ![](data:image/png;base64,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iIiIiEqBo8AJSvCkBlAMnoiIiIiIqJQ4DpxgMDQ5ERERERFRKbANnNQmegyaiIiIiIioVEmJREIWE3Opvb0dgUBATKZpMjIygvLycjF52nzkIx9hwE1ERESGPB4P3n33XTHZtWb6vmw6uXFfGTi5nBsPWiKi6cTrKDnB44ScKKXjxI37attUj4iIiIiIqNRJfX198sTEBMbHx2H01yhN/GuUps9btWqVuF4iIiIiIqKiwaZ6LufGalIiounE6yg5weOEnCil48SN+8qmekRERERERDYYOBEREREREdlg4ERERERERGTDNnDyeDxiEqB7MS4REREREZHbWQZOZoGRx+NBIpFAIpEwnYeIiIiIiMgtLAOnRCIhJmlBk4rBExERERERuZ1l4EREREREREQMnIiIiIiIiGwxcCIiIiIiIrLBwImIiIiIiMgGAyciIiIiIiIbDJyIiIiIiIhsZBw4icOPi8OTExERERERuY1l4KQGSOJ7mtTgiUETERERERGVAsvAKZFIaJPILJ2IiIiIiMhtLAMnIiIiIiIiYuBERERERERkS+rr65MnJiYwPj4Oo79GaeJfozR93qpVq8T1EhERERERFQ0pkUjIYmIutbe3IxAIiMk0TUZGRlBeXi4mExGRQ7yOkhM8TsiJUjpO3LivbKpHRERERERkg4ETERERERGRDQZORERERERENmwDJ/Hlt3pWeURERERERG5hGThZBUZWeURERERERG5iGTglEgkxSWOVR0RERERE5CaWgRMRERERERExcCIiIiIiIrLFwImIiIiIiMgGAyciIiIiIiIbDJyIiIiIiIhsMHAiIiIiIiKywcCJiIiIiIjIhmXgpL7k1uhlt1Z5REREREREbvL/A/qhnVotXEARAAAAAElFTkSuQmCC)

### 資料內容

- Service
  
  1. 取得來源資料
     
     - 透過 clntRepository.findById 取得 clnt 資料。
     
     - 透過 addrRepository.findByClientId 取得 addr 資料。
  
  2. 設定 資料內容
     
     - 根據 樣版檔 的設定，將對應資料 寫入 `context` 中。
  
  3. 最後透過 工具(util) 執行。
  
  ```java
      /**
       * Excel 的 Each 遞迴資料
       * @param clientId 客戶證號
       *
       * @return
       */
      @Override
      public byte[] excelEach(String clientId) {
          Clnt clnt = clntRepository.findById(clientId).get();
          List<Addr> addrList = addrRepository.findByClientId(clientId);
  
          // 設定 資料內容
          Context context = new Context();
          context.putVar("clientId", clnt.getClientId());
          context.putVar("names", clnt.getNames());
          context.putVar("addr", addrList);
  
          return ExportExcelUtil.generateExcel("/templates/sampleEach.xlsx", context);
      }ext);
      }
  ```

- Controller
  
  ```java
    @Operation(summary = "Excel 報表測試: Each 遞迴表格",
            description = "Excel 報表測試: Each 遞迴表格",
            operationId = "excelEach")
    @GetMapping("/excelEach")
    public ResponseEntity<Resource> excelEach(@RequestParam String clientId) {
        var file = exportService.excelEach(clientId);
        return ExportReponseUtil.responseEntity("Each測試表格.xlsx", file);
    }
  ```

- 成果
  
  ![](data:image/png;base64,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)

---

## 範例 - Grid 動態表格

### 樣版檔

1. 於 `/resources/templates/` 新增 Excel檔案 `sampleGrid.xlsx`。

2. 請根據下圖方式，設定樣版檔
   
   - A1 儲存格：設定 掃瞄範圍
     jx:area(lastCell="A4")
     
     - `lastCell="A4"`：模板範圍 (A1 ~ A4)
   
   - A2 儲存格：設定 動態表格
     jx:grid(lastCell="A3" headers="headers" data="dataList" areas=["A2:A2","A3:A3"])
     
     - `jx:grid(...)`：動態表格 的語法
       
       - `lastCell="A3"`：動態表格 的 模板範圍 (A2 ~ A3)
       
       - `headers="headers"`：標題(${header}) 的 Java 端 變數名稱
       
       - `data="dataList"`：內容(${cell}) 的 Java 端 變數名稱
       
       - `areas=["A2:A2","A3:A3"]`：模板區塊
         
         - 參數 1 `"A2:A2"` 代表 標題 的位置
         
         - 參數 2 `"A3:A3"` 代表 內容 的位置
   
   - `${dataList.size()}`：顯示資料筆數
   
   ![](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAsEAAAC6CAYAAABPy5BfAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAFiUAABYlAUlSJPAAAC9aSURBVHhe7d19lBTVnTfwb63jMiOIhTmDgwSnhzEwiIEZURlz9KE7sgvElZeNEvDkmADnAXXPioSgmJAzPVmy6BoX2cRkdROM+WN5iSsv2Sg5kafb4yBDUGcwGsdZYXoyuzIwCbQvOD2hZ+r5Q6q3+nZVdVV3dXVX9fdzTp2evrfqdv26uocvd6qrJXlTSOm+/zkQEREREZWLvxAbiIiIiIj8TpI3hZTYN/eI7Y7be/AFLL71S2IzXdDe3o4rr7xSbPal999/H83NzWKzL/X19WHSpElisy+xVn9irf7EWv2Jtdrj2kxwY/01YhNpKIoiNvlWOdU6PDwsNvkWa/Un1upPrNWfWKs9roVgJ3bWz8opGJZTreX0umet/sRa/Ym1+hNrtYchmKiAyul1z1r9ibX6E2v1J9ZqT8FC8OnTp9PuO7GzflZOs6PlVGs5ve5Zqz+xVn9irf7EWu0pSAg+ffo0uru709qc2Fk/K6dgWE61ltPrnrX6E2v1J9bqT6zVHsdDsF4AhkM76yRZlsWmsrDgsTUZSzmQZTm1qPfd4MbrXlubWKeb3KgVJvW6WTNrLRyxRr/VKtaXrb1Q3KhV5HaNKtbqLL3a9NoKzYlaHQ3BRgEYDu2sn7k1O/rihqdSt+ridhB2q1aVLMuIx+OpxWtv0mzi8Xjqtlh1wqVaYVKvm1hrYRTzvQqXatUeUyvtheJGrSK3a1SxVmfp1abXVmhO1OpYCDYLwHBoZ52i/qJ1+xesGbeDoZbbQdjNWtVjrSXeL6Rivu7dfo0Xs1aU0XGFT2st9nsVLtZaClirPxWrVr33b6E5UasjIThbAIZDO0tkh9mb0qjdacV+3bsZhItdq5tYq7NK4b0Kl2otFazVn4pRq9n7t5CcqDXvEGwlAAPAyMiI2EQabs6OihY8tiZ1moQbilmr28rpdV/MWt0K+irW6k+s1Z9Ya+EUKwDDoVrzCsFWAzAcSuxO0B4wN2fJsnE7GBbzg3Fu11pMpfK6d4PbtcpF+CCGirW6w+263axVW5ubNarcrLXYWGthuP2aFTlRa84h2E4AhkM765Ri/dIpJdoPxrl9TnA5KaXXfaG5XWu8SB8UA2t1jXbCwg1u1qo9psU4tm7WWmystTDU122xspQTteYUgu0GYDi0s04p1i8dM8WeHXUzCBe7VjeV0uu+0IpZq9vvZdbqT6zVn1hrYRUrCDtRq+0QnEsAhkM7my9Z59yVYh08UTkFQ7dqLYVjW+zXvd5rvlCKXStc/PNcKdTqFjdqLYX3KlyqtVSwVn8qVq3FeA87UautEJxrAIZDO0uF4/aH49xi9MbUayuEYr7u3QzAKHKtcLneYtfqJrdqNXqvusmtWksBay2cYr6O3a5Vy+33sBO1Wg7B+QRgOLSz+VAPjHiAjNrd5tbsqHrKg/itcW4GYLdqValvTO3ip7CkfQ0Xo0aVG7XCpF43FaPWYnGrVhT5vQqXajU6pkbtheJGrSK3a1QVq1Y3X7sqN2o1Oo5G7YXiRK2SvCmkxL65R2x33L59+7Bo0SKxmS546aWXMGXKFLHZl7q7uzF37lyx2ZfK6XXPWv2JtfoTa/Un1mqP5ZngfDmR2Im8ppxe96zVn1irP7FWf2Kt9rg2E/yLX/wCd955p9hMFzT9w1Kxydc6vrNbbPKlcnrds1Z/Yq3+xFr9ibXaI8mbQkp8cwS/+93vMDIyguHhYd1bvTbxVq9N20fGHun+D7HJ1zZO+bLYREREROQa12aCd+3ahdWrV4vN5UeSxBYAQG8shtraWkgX+t3+8Jibent7UVtbKzb7Emv1J9bqT6zVn1irPzlRq2vnBNMFBuE2EAikAjAAhEKh1LJu3TqEw+G0JRqNpi2dnZ1p4xERERGRMc4EF4swIyzOBEcikVRfZ2dnxqVWXn755bT78Xg8IwgHAgEEAoG0tsbGRlx22WVpbcFgMO2+LMtobGxMa3OSE/978wrW6k+s1Z9Yqz+xVn9yolaG4GLSBGE1BDspFoshFoultZVCoHbihesVrNWfWKs/sVZ/Yq3+5EStDMHFdiEIFyIEO8nJQP3Zz34WV199dVpbroG61DnxJvUK1upPrNWfWKs/sVZ7GIJLgSSVfAh20qFDh3D+/Pm0tlwDtZMz1IXgxJvUK1irP7FWf2Kt/sRa7SmJEFwOV0TIxomD6RVO1urkDHUhArWTtZY61upPrNWfWKs/sVZ7ih6CtVdEQJYgbDUsW1lP+7hm6+mxMr5dThxMryjVWgsRqBOJBCorKwEHAnWpK9XjWgis1Z9Yqz+xVn9yotacQrAsyxnBIBu9ECwGYC0xYNoJnlbXtbqenny21ePEwfSKcqhVDdSnTp3CFVdcATgQqLVKMVCXw3FVsVZ/Yq3+xFr9yYlabYdgWZaBC/9Y22ElBCuKYhgu7baL9NbTa7Mqn231OHEwvYK12lOIGWotpwK1E7V6BWv1J9bqT6zVn5yo1XYIRgFngs0CpVHoNGoX6a2n12ZVPtvqceJgegVrLZ5CBupEIoHm5mZHAnWpK7XjWkis1Z9Yqz+xVnuKGoIhBGGzQKkXOo1CtLiu1fVUVvbJaNtcOXEwvYK1+oMYqE+dOoWTJ09m/G7IJVDDwRnqQvDzcRWxVn9irf7EWu0peghGnqFTbDcaS1zPSptev8qsLxdOHEyvYK3+lE+tYqCGgzPUKECgzqdWr2Gt/sRa/Ym12lNyIRg6wdIscOr15dom3jdqs9JnlxMH0ytYqz+VSq1uBGoAaaE6n0Bd6krluLqBtfoTa/UnJ2otqRBsNPuq12bWl2ubGMa1rD52rpw4mF7BWv3Jj7UaBere3t60EJxPoHZyhroQ/HhcjbBWf2Kt/uRErZ4IweI62dpzbRPvm7GzrhVOHEyvYK3+xFrNGQVq8XeplUDd2HgNOjt/n9ZGRJSvxsZGdHR0iM0lKZffw6KihWCj4GsULu2059om3jdjZ10rnDiYXsFa/Ym1ukeSJChKr9hMRJQXSap1LNcUmhO/h/9CbHCbGia1PxfrAIhhWP1Ze5+IiIiIvM92CFa/LEO9dVquAVgNqmKAFWVbTxuEix3KiYiIiKgwbIfgeDyeWvKhKEpq0bbZJY6jvS+ObWW9bH1ERKWqp6cPl18+Azt37he7PCOZTOLOO+/Fb3+bfh40uSORGML06XMxMPAnsYvId2yH4EKwEjbVfr3ZXbdxhpiIqDAeeugRTJ8+BTfe+OkVMZLJJBYuXAVJqsWYMdMcC2fZwt769ZshSbWpJdt/LMz206wvm7a2oxgzZhref/8UFi5chXB4a1r/9u27MWrU1br/aVAfV3xMszErK0fhqae2YP78u/HJJ4OpdiI/KokQbJWVsOyGUtkPIiKturpJOHPmTSxbtlDs8oS2tqM4ceIPCIfXpdoqKiqwf/9PMTjYjdraiWnrF8r69Ztx4sQfoCi9qSXbc2q2n2Z9VkydOhljxowGADQ01Kfak8kkXnqpDTt2/BAvvBDRbPGpn//8efz5z+d1H9NoTAC4+eYbEAzehP37f5PWTuQ3krwppLx615MYHh7GyMiI4a1em3ir16btW7Nmjfj4pCFePomISE8gEEhdHSKRGMLUqUH84Q/vAwD+8i8vxiuvPIcbb2xEW9tR3Hrr8tT97dt34/77W9DT04bq6s8AF06h+MIXlmDfvp+kZl9xIQj+8z//W+r+jh0/SAXBb33rn/D5z0/F3/3dd3D27Ae47rpr8corz+GSS6oy9ke7ndmYav8NN8zQDZyJxBBmzboN0eiu1L4jy5g9PX2YNes2nD37AQCgpeUBhMPrMrYBgMWL52HPnqfR09OHOXOW4vXX/zPtcVRm9an9evuZrc9IW9tRrF0bRiSyC1/96lrcddeitPo2b/4XbNp0P1av3oh9+36CSy6pSvXNmbMUbW3/gQUL7k57TLMxxcdVjyuVB0mqLassktMl0nKxa9cuXP1/7hSby84Xp10uNgEApAszzOVwqoUTlzXxCtbqT8WuVTK4RFoymcTy5X+PDRvWpAJtW9tRPP74v2HHjh/oBjCjEKwlBsP16zcjGj2cCkhqeL3jji/h7ru/gW3bWlBd/RnT0CeOabYuLAZI7Zjjxl2W8VxoGY1nFv6SyWTW+vTaVHp9YlAHkPafCjPbt+/GJZdU4o47vpRWazKZxN/+7RrcddciLF48L+MxrdDbV/I/XiKNCur/vXNGbErRnu987733IhwO48CBA2hvb09bj4jIqptvvgG33z4XVVVT8J3v3J8RaOrqJuHkydcygmJb29HU+bCTJ9+MM2fSPwy9YcOaVEh7/PFNWLZsIfr6TuLAgSjGj78OklSLqqopeO+9GHp6+iyNmQujMSsqKrBgQQizZy/KOI82V9nqy4V6Cov21IvXX/9V1gCcTCbx4osRTJ58VapW9ZSIn//8eVx00UUZs7t2nT+fzKs2olLHEFwEZkFYNXPmTADAtm3bsG7dOkiShAkTJiAUCjEgE5EtF19cgRUrlqKr67jYpSuRGMLKlRtw5Mg+KEqvrXNZZ836PM6d60oFuqGh93DjjY15jWkk25grVy6FovTiqqsmQpJqsWSJ/reWak2cWIOBgTM4d+4TsQswqS9X6hU9tB/CmzXrtqwfSuvrO4mDBw9h9uxFkKRarFq1Ab/85Uv48MOPsXfvr7F3769TQf33v/8vjB9/XdYP94kuvrgCdXWTxGYi32AILhK9IKy9LNs999yDcDiMF198EYcPH4aiKOjo6EBLSwsDMhFZ1tZ2FM8/fwBPP70Fb7zxVkYQ6unpw4QJ1+teXUD17W8/ht7e/xGbM0yYMB7d3ScsfaBKHLOi4iLU19fi4MFDaevZIY6pWrlyKU6caMPHH59LC5d6M52TJk1AY+M1WL364bR22KzPqlxngiORw7j//hWpbQYHu5FIDGFoaAj79/80rf2aaz6H06ffsDUzfPLkaVx55RUYPfoSsYvINxiCi0gvCJupqalBMBhkQCYiS9rajmL+/Lvx9NNbUFFRgd27f4SHHtqS9fSAyspRuP32ualZxoqKiyzN2lZWjkI0uhv33bcpY1Yz25gVFRV48MF78NhjT6UF1URiCLW1N6XNaFoZU91O3Y+GhhC+970NqXBZWTkKGzfel9pWnSWuqKjA888/heHh4bTZ2Z0795vWZ7afZn25SCaT2Lv312lXdaisHIX584N5/SdC64c/fBarVn0laxgn8jJ+MK4EfHHa5akPxhVCf38/urq60NXVhf7+fhw5cgTxeBzt7e2oqalBQ0MDGhoacMUVV6C5uRmyLKO5uVkcxjFOnMzuFazVn4pdq9EH4/zwYab16zfj0ktHp10mjdxl9uFA8jepzD4YV/QQfOu0y3HQ5oyoH9067fKivPDMArIsy2hsbEQgEEBtbS0aGxshyzKCwaA4jC1OvHC9grX6U7Fr1YbgtrajuOWWO1J94iW7vEbvChfkHj/8R4pyxxBcIHoh+FbN5cLMgrAYlMX7haDuW76PY3VfixWCzcTjcXR2diIWiyEWi+HYsWOIx+OIRqN5BWQnXrhewVr9qdi1Gs0EExHlgyHYhCzLqZ/jcXuXthFDsBoOtbfQCZ1iiDRarxDEx86VlXFKMQSbyScgO/HC9QrW6k/FrpUhmIgKodxCsOUPxsmyjHg8nlq0gdgJ2UKiyup6VFhqqP3617+OcDiMPXv2IBKJQFEU9PT0oKWlBXPmzAEAPPvss2htbYUkSRg3bhyWLVuGFStWIBwOY+/evYhGo+LwRERERAVlOQSLihmEqbRlC8gPPPCAYUAOhUIMyERERFRwlkOw3dMfstGeApGrW6ddbjiGUZ/artcHk+1Uev3qfb0+SqdeecIoIBvNIFdVVSEUCmH58uUIh8PYuXMnotEoEomE+BBEREREWdk6J1ikniJhhXhOsEoNjUazwLfqnE8rbiOuo71v9HO2++Jj6PUb9YnM+lS3euyc4Hzkch5PIpFAe3t76moW7777Lvr7+1PXO25ubkZNTQ2mTp2KhoYG1NTUoLm5GZWVleJQrsqlVq9ire7hOcFEVAg8J9giOwE4G6NZYbPwaNQubqMd22gbGGyXrV9vn/XYWZf0VVZWIhgMYtmyZQiHw9ixYwcikQgGBwdx9uxZtLS0YNGiRQCAffv2obW1FePGjeMMMhEREenKKQQ7GYBVTgbFWzWnPOiNadSeTbZxqTgYkImIiMgu2yG4EAHYSK7B+OA7ZzIWlTqjK870WiGOaXUMcRaZ3MOATERERHpshWC9AJzLFSJyCbZOYBglrVwDsiRJCIVCWLJkCcLhMH72s58hGo1mvDeIiIiodFkOwUYBWGyzSgzCTgVUvdlj8b5em7hdtn7orEP+YRaQFUVBS0sLvva1rwEAXn75ZbS2tqKuri4jID/33HMMyERERCXI8tUhjGZ8rf7jrr06hBp4tSHSKACL4VjcRryvytau3TaXfm27XpuWWIOeW3l1CN9QQ29nZyfefvtt/PGPf0RnZyfi8TiCwSBkWcbMmTMRCAQQCARS36jndX4/rlrFrpVXhyCiQii3q0NYDsH5MrtEmllAzNbvBVZqYAj2J7FWbUDu7e1FLBbzTUAWa/WzYtfKEExEhcAQXCBGIdgKKyGyVFndd4Zgf7JTq9cDsp1ava7YtTIEE1EhMAQXSD4huBwwBPuTU7WaBeTm5mbIsozZs2ejpqYGDQ0NqS8McZNTtXpBsWtlCCaiQijLEPzqXU9ieHgYIyMjhrd6beKtXpu2b82aNeLjk0YsFhObiLLq6OjAhx9+iI6ODgwMDOD48eM4fvw4BgYG0NTUhLFjx6KpqQnV1dWor69HfX09qqurxWHIQwKBAEMwETlOkmrLKou4OhO8evVqsZku+HRmxxv/+8qXE/9784pi19re3o54PI729nacOnUKXV1d6OrqQn9/v+MzyMWu1U3FrpUzwURUCGU5E8wQXHwMwf5UyrU6HZBLuVanFbvWcePGWb4yDxGRVbIs4+zZs2JzSXLi9zBDcIlgCPYnr9ZqNyAHAgFIkuTJWnPh1eOaC9bqT6zVn1irPZa/LIOIykdzczPmz5+PcDiMH//4x4hEIjh58iQURcHWrVuxdu1aAMCxY8fQ2tqKUCiEQCCApqYmhEIhhMNhPPHEE4hGo2V1fhkREXkHQzAR2aIXkHt6ehCLxfDMM8+gpaUFuPC/dDUgS5LEgExERCWFIZiIHNPY2IhgMIhwOIytW7emArKiKAzIRERUUooegiVJEpuIyIcYkImIqJQUNQSrAThbEM7Wb4UkSWnjiPe17UTkLicDcldXlzg8ERFRBtshWJbl1JIP7dUQFEUxDaVOXDVBHEO8r1L3hYhKg92AvGTJEkiShGnTpiEUCuHhhx/GI488woBMRERpbIVgWZYRj8dTS75BWMsolBIRGdELyO+88w4URcGePXvQ0tKCyy67DB988AEDMhERpbEVggt9cXYGYSJySkNDA4LBIDZu3IgtW7YwIBMRURpbIdhJ+Z52oJ4+oTeGUTsRERwIyI8++igDMhGRx+UcgtVTI/KhzvzaDazqecLqot1e22d3XCIiKwF57NixhgFZO4Pc2dkpDk9ERCXCdgh24kNxIqPAqgbabG3aMK3tMxo3m1y3IyJ/UwPyvffeaxiQtTPIK1asgCRJqKurQygUwrp16xAOhxmQiYhKgO0QXMgPxjkRPCXNaRJOjEdEZIXeDHJHRwcURUEkEkFLS0vqe+4ZkImIis92CHZTLsFYPRVCu9glzigTEeUjEAggGAzigQceQDgcZkAmIioBRQnBdoMtEZFfMSATERVHziE43w/GiUHY6uyr3uywet+sj4jIa5wMyO3t7eLwRERlzXII1n5TXL4BGEJgtRqAVeq26qLd1qhP+1h6t0REXmI3IK9btw6SJGHChAkIhUK49957EQ6HceDAAQZkIipLkrwppMS+uUdsd9yuXbuwevVqsTkjxIqy9RdCuTxmsfT29qb+cfY71upPXq61v78fXV1d6OrqQn9/P44cOYJ4PI729nbU1NSgoaEBDQ0NuOKKK9Dc3IxEIoHFixeLw/iSl4+rXazVn1irPZZnggslW/DTO8WhkMopjBJR+ampqUEwGMQ999yDcDiMF198EYcPH4aiKOjo6EBLSwtmzpwJANi2bRu++93vcgaZiHyp6CHYCjdDqZuPRURUSvQC8p49ewwDMk+xICIvk+RNIeXVu57E8PAwRkZGDG/12sRbvTZt35o1a8THJ41YLCY2ERGVvIGBARw/fhzHjx/HwMAAOjo68OGHH6KjowPV1dWor69HfX09qqurcc0112Ds2LFobm4WhyEiclXRzwkm8jMnzlnyCtbqT/nWKp6DfOzYMcTjcUSjUciyjMbGRgQCAdTW1qKxsRGyLCMYDIrDuCLfWr2EtfoTa7XHE6dDEBGRN4mnWOzZsweRSASKoqCnpwctLS2YM2cOAODZZ59Fa2srJEnCuHHjEAqFsGLFCoTDYezduxfRaFQcnogoZwzBRERUFOqs79e//nUGZCJyXUmEYL2rP+i1kbdpj6nRz3r3iaj8MCATUaGVRAh2CsNT6ZMufImJ+rPYrtdHRKTlVEDeuXMnotEoEomE+BBEVAZy/mCc3W+NM/tgnKRzbV69NjNiaNLb1mhMdVu9vlxo98VsTKP9yZeT42Z7bqw+lrqeuL72vtjnB06cuO8VrNWf/FRrPB5HZ2cnYrEYYrEY3n33XfT396cu56Z+SG/q1KloaGhATU0NmpubUVlZKQ7leX46rtmwVn9yotacQrAsy8CFXyhWFTIE6wUscXvxvihbv11Oj1cMZs+nllkfdPr1jpfY5xdOvEm9grX6U7nUmkgksG/fPiiKgq6uroyA3NzcjJqaGt8E5HI5rmCtvuVErUU7HULS+fO3WVupcHtf3H48lRhG1dCaC22Q1h5bbZu6+CkAE5F3VFZWorm5GcuWLUM4HMaOHTsQiUQwODiIs2fPoqWlBYsWLQIA7Nu3D62trRg3bhyqqqoQCoWwfPlynmJB5DG2Q7Dd0yCMKIqSWsT7em125bINFZbTx5iIyA2VlZUIBoMMyEQ+YzsElyIlj1lKo5lmvXZxFlP9WVzPKnE7cSy9xxOJ26htYr920evTkgxmZHN9nsXH1z5mtvtERKXMqYD8s5/9DNFo1JFJJiKyxlYIdmoWuBDU0GYnPKlhTwx82nbteNrZS7P1rBDX1xtLfDyR3jbiuDCYbdVum8v+26F9HHFfst0nIvIqOwH55ZdfRmtrK+rq6iBJEkKhEJYsWcKATFRAtkKwU6QsM4JmbdnoBTo18GVrUxm1i6yup8dsW7M+I9oQqdeOLDXnQ+8516N3PMX7RETlQAzIzzzzDCKRCM6ePQtFUdDS0oKvfe1rAAMyUcFYDsHqFSFkWU77ORfa2T47i1ViKBPvW2E1mFldLxt1H+2MZWcbSScAOxVA9cbWo3c89dqIiMpdMBjE4sWLGZCJCiinS6Qhh1MjCnWJNO16Rj/bvW/0s3jf6Ge9+3rt+awjMttGvG/UprLbp9cmkmwE7WxjeYkTl3DxCtbqT6zVW9TQ29nZid7eXsRiMXR2diIejyMYDEKWZcycOROXXnopZs2ahcbGxpwntLzCD8fVKtZqj+WZ4EIxClBKDrO3KqMxtfIZHzZDncpsv7KNp/Zr18u2DXQe02gbbbvRcyOOZYdiMLMvtuU6PhERWZ9Bbm9vN5xB/td//VdEo1H09/eLwxP5SlFDcLZQpYYxvUCmEtfJNqaWdlv1Z7N2ldputJ72VruIY2hvxbHEdcU2GGwjjqv+LO6Ddluj8Z2ifRxxX8Q2cT+JiMgZ2oD8/e9/3zAgHzt2DK2trWhqaoIkSbjpppuwYMECBmTynZxPh7BLezqEGnKsBi+rIc1sPbM+0qd9zsyeP7M+M7lu5yVO/LnGK1irP7FWf7JTa3t7O+LxONrb23Hq1Cl0dXWhq6sL/f39aG5uhizLmD17NmpqatDQ0JD6Rr1SYadWr2Ot9hRlJlix+Wdvq+uarafOfJJ12tlio+fWrC+bXLcjIiL3NDc3Y/78+QiHw/jxj3+MSCSCkydPQlEUbN26FWvXrgU4g0weJMmbQsqrdz2J4eFhjIyMGN7qtYm3em3avnnz5omPT0RERD7U0dGBDz/8EB0dHRgYGMDx48dx/PhxDAwMoKmpCWPHjkVTUxOqq6tRX1+P+vp6VFdXi8MQFUxRToegTE5M63sFa/Un1upPrNWfil1rtlMsKisrMWfOHMiyjMbGRgQCAQQCAXEYS4pdq5tYqz1FOR2CiIiIyle2UyxaWlqAC0GntbUVoVAIkiShqakJoVAI4XAYTzzxBKLRKGKxmDg8kSUMwURERFQympubEQwGEQ6HsXXrVkQiEfT09EBRFDzzzDMMyOQYhmAiIiLyhMbGRtsBORAIMCCTLoZgIiIi8jyjgByLxQwDMmeQy5utECzLcsZCREREVMqMArLZDLI2ID/88MN45JFHEI1G0dXVJQ5PHmUrBANAPB5PW4iIiIi8ykpAvuyyy/DBBx+gtbUVS5YsgSRJmDZtGgOyx9kOwURERETlQA3IGzduxJYtWxCJRPDOO+9AURTs2bOHAdnjbIdgngZBRERE5a6hoYEB2eNshWDtaRAMwkRERESZGJC9wVYI1mIQJiIiIrIn34C8bt06hMNhRKNRdHZ2isOTDTmHYCIiIiJyjpWArH5VcGtrK1asWAFJklBXV4dQKITvfve7DMg2MAQTERERlTg1ID/wwAMIh8OIRCLo6OiAoiiIRCJoaWnBZz/7WcAgIHMGORNDMBEREZGHBQIBBINBrFy50jAgm80gl2tAthyCxfN/ZVnmdYKJiIiISpgakM1mkMs1IEvyppAS++YesV2XNgjbDcC7du3C6tWrxeay19Q0HZ2dvxebiYiIiErSmDFj8NFHH4nNrurt7U2F91zZCsH5YAjWJ0kSFKVXbCYiIiIqSZJUC0VRxGZXORGCLZ8OQURERETkF5K8KaS8eteTGB4exsjIiOGtXpt4q9em7Zs3b574+GUvEAhwJpiIiIg8Q5JqEYvFxGbP4ekQRWZ0OkRPTx9mzboNP/rRZixbtlDsdk0++5FMJrF8+d9jw4Y1uPHGRrGbiMgT8vk9SORHPB2C6IJEYgjTp8/FwMCf0tofeugRTJ8+JRWAk8kkFi5cBUmqxZgx0zLWz5XR46vWr98MSapNLTt37hdXSWO2n2Z92bS1HcWYMdPw/vunsHDhKoTDW9P6t2/fjVGjrsZvf/u/n77VPp4k1Wb0ZxtTT7bnywluPIYZo+fFjf1y4zGM6rPDzn7aWdcqqzXYeWw762pt377b8PFzZbYvVmsnosJiCC5RdXWTcObMm0Wfdch1P9rajuLEiT8gHF6XaquoqMD+/T/F4GA3amsnpq1fKOvXb8aJE3+AovSmlmy1mO2nWZ8VU6dOxpgxowEADQ31qfZkMomXXmrDjh0/xAsvRFLt6uOp+37w4A5s2fKjVD9Mxix3fn9e/FCfV2rI9fegGa/UTuRnDMElJJEYQm3tTbqzfm1tR9Pub9++O2MmsqenDxMmXJ82U6i2X375jLTZUHXm4Vvf+ifs2LEv1T9r1m345JPBrDOe2jGrqqagt/d/0vr37Pk1vvKVv0lry8ZsxlasQd3/9es3o6pqCn7/+//C+PHXQZJqsWTJp6fd9PT04Re/+BWefnpLahwt8fnONkNcSH19J1FVNQpNTdNx6NBr+OSTQXEVAEB3dw9mzpwmNufkJz/ZqVu72fNi9RiJrwmzMY1eg+KY2uOer3Kt3Ww/jeoze4+ZbZePUtlPO78HJYu/l4iohMibQko8Hi/48tRTTymUCYCiKL0Zy/nzx5U77viScuTIvlTbK688pyxePE8ZHOxWrrnmc8rp02+kbXPiRJtSU1Odts3gYLfyuc/VpdrUMdT+b3zj/yrXXXetcu5cV+r+jh0/SNtefCxxTHEd8b64ZOtXLtQyadKVyunTb+g+F1bGe+WV59Jq0y7nzx9Xli9fZLrPem1mfSdOtCnjxl2mAEgtRo8vLj/96WPKjh0/0K11cLBbueqqK22NZ7ao46mvgxMn2pS5c29Wzp3rsvS8qIv2GJm9JrKNafQa1HsutI+dy3Pth9pzXcz2U1xXW1+2dY22y+cYubmfyoX3X0vLAxnrqYveuNmOkd42XLj4Zfk0uxRXLBYTm2yzPRMsy3LaQu65+eYbcPvtc1FVNQXf+c79qK7+TFp/Xd0knDz5mu0PoW3YsAaXXFIFAHj88U1Z/+T32mtvpp3r65S2tqOpGZXJk2/GmTOffiFLRUUFFiwIYfbsRY7MhuHCzOuBA9HULE1V1RS8914MPT194qqWqX8y1Z568frrv0o9t0aSySRefDGCyZOvStWqPSWisnIUensPQ1F6sW1bGLfccofhTLFVY8aMTs2QT5gwHu+/fwrnzn2S9XkxOkZmr4lsY8LgNWh23HN9ruGD2nNltp8wqS8bo+1yPUZu72euCnGMiMhdtkKw+lXJ2oXcdfHFFVixYim6uo6LXboqK0fh9tvnYvbsRZCkWsyff7fh6QHFlEgMYeXKDThyZB8UpTfjnNuVK5dCUXpx1VUTIVn80+LEiTUYGDiDc+c+EbsAALNmfR7nznWl/oEeGnrP8B9eK8Q/jUrCn7eN9PWdxMGDh1LHaNWqDfjlL1/S3e7662fgo4/O4a233hW7HGP0vGQ7RmaMxszG6Ljn+lxnY7SfpVR7IeRan9l2hThGZo9nJtftsnHzGBGR8yyHYDUAU/G0tR3F888fwNNPb8Ebb7yVcU5bj845wT09fXjzzXdS/wh//PE7GTPIdk2cWIPXX/8dBgb+hGQyiaVL70s7Z6+i4iLU19fi4MFDadvZ8e1vP5ZxnjEu/KNz4kQbPv74XNo/pufPJzNmcSdNmoDGxmuwevXDae24MAPY3X0C+/f/RuzKWa4zX5HIYdx//4rUNoOD3UgkhnTD+2uvvYlLLx2Na6+dKnY5ws7zoj1GZq8JO2MaEY97rs+1GTv7Wczac2W2nyK995/ee0yk3S7XY+T2fjrB6BhZ2RciKh7LIVjF0yCKo63taGoWt6KiArt3/wgPPbQl65/h6uomYcaMaRg9uiE1GyNeZkuP+mEe7Yc71FmcurpJCIfXYfz463DxxfVYsmRe2qxKRUUFHnzwHjz22FNp/yCYjSnOWFdUXJQaU/xgUUNDCN/73obUP6aVlaOwceN9qW3V2ZiKigo8//xTGB4eTpuN2rlzPyorRyEa3Y377tuUMUtltp9mfblIJpPYu/fXaZ8Or6wchfnzgzh48FBG7WvXhvHKK89lDRK5MntezI6R2WvCbEwzYu3icXea2X76ofZs+2lUn9qv9x7Ltl0uirWfra1PpJ5v9UNwZu/3bMfIaF+IqHRY/rIMNfiqs8F2Z4b5ZRn6jL4sI5EYwqxZtyEa3ZXXzG1b29GM4LR+/WZceunotMuXFYJbj0NERETukcrxyzK0oTcej3NG2GHaD24YffjNruuvn4E//vFM2kyweP3eQnn00Y14++3urLPORERERG6zNRMszvzqtRnhTLA+o5lgIiIiolJUljPBRERERER+wBBMRERERGXHcggWzwG2cyoEEREREVEpsRyCoQnCDMBERERE5GW2QjAuBGEGYCL/SiSGMH36XAwM/EnsIiIi8g3bIZgIF77kYeHCVZB0vnyjre0oxoyZhvffP4WFC1dlfKHH9u27M7aB8DWrVr5QQCuXMbPtp5Fs2+nti/b50nvOssllTLP91F7oX3xeKitH4amntmD+/LttHQMiIiIvkeRNIeXVu57E8PAwRkZGDG/12sRbvTZt37x588THL3uBQMCTl0hLJpNYvvzvsWHDGtx4Y2Nan/oFHZHILnz1q2tx112LsGzZwtR2d9/9Ddxxx5fw5pvvpK5XrLZv29aC6urP2PqijVzHNNtPM2bbGe2LqK3tKB5//N+wZ8/TYleGXMc0209VT08fVq/eiH37fpLxbWTr12/GDTfMyNiGiIjKmyTVIhaLic2e8xcAcOWVV2LSpEmora3F5MmTcfXVV2Pq1KmYNm0arr32WsyYMQONjY2YNWsWbrjhBjQ3N+MLX/gCbrnlFsyZMwdf/OIXMXfuXPz1X/81FixYgNtuuw0LFy7E4sWL8eUvfxl33nknAKC2tpaLsIjWr9+cmtmTLnzFr+pb3/on7NixT3dmU/wKT+12ZmNqZ0olqTZjVtNpfX0nUVU1Ck1N03Ho0Gup/a+oqMC///u/pL4cZPr0KTh27J20bXt6+jBhwvUZM6j5jOk0o30RdXf3YObMaWltdusT6Y2ZjyVL5mV89TURERFKINM5sQ88HaLEPP74JihKLxSlFydOtOHBB7ekzs0cGvozvv/9p/Hf/30EitKLYPAm7N//GySTSaxcuQGvvfafUJReDA524x/+4V9S2xmNmUwm8eCD/4gDB36e6ldnGcVwLIZuMzfffANef/1XGDt2DPbv/2naTGIkchh/9Ve3YNKkCZDlsXjrrXfTtlW9/XY3vvKVvxGbdeU6ptl+mjHbzmxftP9RefLJZ/Hgg/ek+szkOqbZflpx/fUzkEgM4dy5T8QuIiIiz2MILjHar06ePPlmnDmT/iHEDRvWpP5s/fjjm7Bs2UL09Z3EgQNRjB9/HaQLX7n83nsx9PT0mY5ZUVGBBQtCmD17UcYMcF3dJJw582YqHCtKL15//VcZfzK3I5lM4sUXI5g8+arUY7/wQkRcDdu370Y0ehgLF/5VWntd3SScPPla2ukX+Y7ppGz7Ulk5Cr29h6Eovdi2LYxbbrkj7T8VudSXbcx8nT+fTL2OiIiI/IQhuIQkEkNYuXIDjhzZl5rRra2dKK6ma9asz+Pcua5UYB0aeg833tiYdcyVK5dCUXpx1VUTIUm1WLLk06+2zmcm2Ehf30kcPHgIs2cvgiTVYtWqDfjlL19KG3P79t148sln8corz1kK3IUYM1dW9kV1/fUz8NFH5wxnrVWFGNOOiy+uQF3dJLGZiIjI8xiCS9i3v/0Yenv/R2zOMGHCeHR3n8D+/b8RuzIYjbly5VKcONGGjz8+h08+GSzITHAkchj3378iNd7gYHfan9uzhVW9c2bzHdOMOoOuPYfaTLZ90XrttTdx6aWjce21U1NtudSnpTdmPk6ePI0rr7wCo0dfInYRERF5nuUQrH5JhriQcyorR+H22+emZv0qKi6yNBNcWTkK0ehu3HffpoxZW7MxxQ/TNTSE8L3vbbAdFq1IJpPYu/fXaGioT7VVVo7C/PlBHDx4CInEEFpbt+KNN97C6NENkKRajBkzzfRatYUYU2vixBqMG3cZurqOi10ZrOyL9rleuzacNZgXYkw7fvjDZ7Fq1VccG4+IiKiUSPKmkBL75h6xPYPet8TptRnZtWsXVq/+9E/t9L8kSYLis0uk+UUiMYSpU4N49NGHbX+ozCuMLpGmXl7NyVBNRET+IEm1UBRFbHZVb28vanWusmWH5ZlgItHQ0J8xe/aijC9p8IPt23ejqmoKVqxY6ssArM4i6334MpEYwpo1D+PAgZ8zABMRkW9ZngkW2ZkFBmeCDXl1JpiIiIjKE2eCiYiIiIg8KqcQbHcWmIiIiIiolOQUgomIiIiIvIwhmIiIiIjKju0QzFMhiIiIiMjrbIdgIiIiIiKvYwgmIiIiorJjOwTzVAgiIiIi8jrbIZiIiIiIyOskeVNIefWuJzE8PIyRkRHDW7028VavTds3b9488fHLXiAQ4DfGERERkWdIUi1isZjY7Dk5f22yXfzaZH382mQiIiLyEr98bTJDcJGNGzeO51kTERGRZ8iyjLNnz4rNrnIiBPOc4CI7e/YsFEVBLBaDoihlsbBWfy6s1Z8La/Xnwlr9ubhVa7EDsFMYgomIiIio7DAEExEREVHZsR2CZVmGLMtiMxERERGRZ9gKwbIsIx6PIx6PMwgTERERkWdZDsFqAFYxCBMRERGRV1kOwUREREREfmE5BIszv+LMMBERERGRV1gOwdAEYQZgIiIiIvIyWyGYH4wjIiIiIj+wHILF2V8GYSIiIiLyKsshmIiIiIjILyyHYHHmV5wZJiIiIiLyCsshGPxgHBERERH5hK0QjAtBmAGYiIiIiLzMdggmIiIiIvK6/w9dl3q+WeTvyQAAAABJRU5ErkJggg==)

### 資料內容

- Service
  
  1. 取得來源資料
     這裡透過 clntRepository.findAll 取得 全部的 clnt 資料。
  
  2. 整理數據
     
     - 使用 Grid 時，`樣版內容` 的數據，
       要使用 `List<List<Object>> dataList = new ArrayList<>();`。
     
     - 每一筆資料都是 `List<Object> data = new ArrayList<>();`
       且 寫入順序 就是 資料呈現的順序。
  
  3. 設定 資料內容
     根據 樣版檔 的設定，將對應資料 寫入 `context` 中。
  
  4. 最後透過 工具(util) 執行。
  
  ```java
      /**
       * Excel 的 Grid 動態資料
       *
       * @return
       */
      @Override
      public byte[] excelGrid() {
          List<Clnt> clntList = clntRepository.findAll();
          // 設定 headers
          List<String> headers = Arrays.asList("姓名", "客戶證號", "性別");
          // 設定 數據集合
          // 1. 要用兩層 List 進行封裝
          // 2. add 的順序 = 資料顯示的順序
          List<List<Object>> dataList = new ArrayList<>();
          for (Clnt clnt : clntList) {
              List<Object> data = new ArrayList<>();
              data.add(clnt.getNames());
              data.add(clnt.getClientId());
              data.add(SexEnum.getDescByCode(clnt.getSex()));
              dataList.add(data);
          }
  
          // 設定 資料內容
          Context context = new Context();
          context.putVar("title", "Grid 測試表格");
          context.putVar("headers", headers);
          context.putVar("dataList", dataList);
  
          return ExportExcelUtil.generateExcel("/templates/sampleGrid.xlsx", context);
      }
  ```

- Controller
  
  ```java
      @Operation(summary = "Excel 報表測試: Grid 動態表格",
              description = "Excel 報表測試: Grid 動態表格",
              operationId = "excelGrid")
      @GetMapping("/excelGrid")
      public ResponseEntity<Resource> excelGrid() {
          var file = exportService.excelGrid();
          return ExportReponseUtil.responseEntity("Grid測試表格.xlsx", file);
      }
  ```

- 成果
  
  ![](data:image/png;base64,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)
