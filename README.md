# TrainNavigation

## 概要

**TrainNavigation** は、任意の2駅間における最適経路（最短経路）を探索する、鉄道ルート探索システムです。複数の路線・駅をノードとするグラフ構造に基づき、経路計算にはダイクストラ法を用いています。

主な特徴：

- 駅間の所要時間または距離に基づく経路探索
- 隣接リストによるグラフ構造の構築
- 複数路線の接続を考慮した乗換案内の基本モデル
- コマンドラインベースのシンプルなUI

---

## 機能

- 駅名を指定して経路を探索
- 経路の総移動コスト（例：所要時間、距離など）の出力
- 入力ファイルによる路線・駅データの自由な拡張

---

## 使用技術

- Python 3.x
- 標準ライブラリ（`heapq`, `collections`, `csv` など）

---

## ファイル構成
TrainNavigation/

├── main.py # メインスクリプト

├── graph.py # グラフ構造と経路探索クラス

├── station_data.csv # 駅と路線の接続情報

└── README.md # このファイル


---

## 使用方法

1. リポジトリをクローン：
   ```bash
   git clone https://github.com/Hoshikoshi6321095/TrainNavigation.git
   cd TrainNavigation

3.必要に応じて station_data.csv を編集（駅名、所要時間、路線など）

3.実行：python main.py

4.コンソールに従って出発駅と到着駅を入力すると、最短経路と所要時間が表示されます。


