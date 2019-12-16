package ca.georgebrown.gbcresearch

import grails.util.Environment
import grails.util.Holders


/**
 * Created by Tony on 2015-11-21.
 */
class Benchmark {
    static def start = 0
    static def lastTime = 0
    static def startTimer()   {
        start = System.currentTimeMillis()
        lastTime = start
    }
    static def elapsedTime() {
        if (!lastTime) startTimer()
        def elapsed =  System.currentTimeMillis() - lastTime
        lastTime = System.currentTimeMillis()
        return elapsed
    }

    static def totalElapsedTime() {
        if (!start) startTimer()
        def totalElapsedTime =  System.currentTimeMillis() - start
        return totalElapsedTime
    }

    static def printElapsedTime(String processName = '') {
       println "${processName}:${elapsedTime()} milliseconds"
    }
    static def printTotalElapsedTime(String processName = '') {
       println "${processName}:${totalElapsedTime()} milliseconds"
    }
}
