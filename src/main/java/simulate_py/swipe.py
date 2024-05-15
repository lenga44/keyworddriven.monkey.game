# -*- encoding=utf8 -*-

from airtest.core.api import *
from airtest.cli.parser import cli_setup
from jinja2.compiler import generate
from poco.drivers.unity3d import UnityPoco

def connect_device(did,root_folder)
    if not cli_setup():
        auto_setup(__file__, logdir=False, devices=["android://127.0.0.1:5037/"+ did +"?touch_method=MAXTOUCH&",]
        , project_root=root_folder+"/tool_test_game/simulate_py")


def swipe_input(x1,y1,x2,y2):
    poco = UnityPoco()
    """ swipe([1000,400],[400,400]) """
    swipe([x1,y1],[x2,y2])

def main():
    print(sys.argv[1])
    connect_device(sys.argv[1],sys.argv[2])
    swipe_input(sys.argv[3],sys.argv[4],sys.argv[5],sys.argv[6])

if __name__ == "__main__":
    main()
